package com.airnz.tpx.commserv.adapter;

import com.airnz.tpx.commserv.data.EmailRepository;
import com.airnz.tpx.commserv.exception.ProcessingFailureException;
import com.airnz.tpx.commserv.pojo.EmailMessageDTO;
import com.airnz.tpx.commserv.pojo.EmailSearchCriteria;
import com.airnz.tpx.commserv.util.EmailUtil;
import generated.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.airnz.tpx.commserv.common.CommunicationServConstants.*;

@Component
public class EMailAdapter {

    @Autowired
    EmailRepository emailBoxRepository;

    /**
     * Sending a Mail
     *
     * @param msgRequest
     * @return
     * @throws ProcessingFailureException
     */
    public MessageResponse pushMessage(MessageRequest msgRequest) throws ProcessingFailureException {
        EmailMessageDTO emailMessageDTO = getEmailMessage(msgRequest);
        MessageResponse messageResponse = new MessageResponse();
        if (saveMessage(emailMessageDTO)) {
            messageResponse.setId("" + emailMessageDTO.getTimestamp());
            messageResponse.mailLocation(MAILBOX_SENT);
            messageResponse.setContent(msgRequest);
        }
        return messageResponse;
    }

    /**
     * Get mail from a mailbox
     *
     * @param emailSearchCriteria
     * @return
     * @throws ProcessingFailureException
     */
    public MessageSearchResponse getMessages(EmailSearchCriteria emailSearchCriteria)
            throws ProcessingFailureException {
        MessageSearchResponse messageSearchResponse = new MessageSearchResponse();
        Map<String, Map<String, List<EmailMessageDTO>>> messages = emailBoxRepository.getMessages();
        String userId = emailSearchCriteria.getAuthorization().split(" ")[1];
        String mailbox = emailSearchCriteria.getMailbox();
        if (userId != null && !userId.isEmpty() && messages.containsKey(userId)) {
            Map<String, List<EmailMessageDTO>> mailboxMessages = messages.get(userId);
            if (mailboxMessages.containsKey(mailbox)) {
                prepareSearchResponse(mailboxMessages, mailbox, messageSearchResponse);
            }
        }
        EmailUtil.prettyDisplayMailbox(messageSearchResponse);
        return messageSearchResponse;
    }

    public MessageResponse saveDraft(MessageRequest msgRequest) throws ProcessingFailureException {
        EmailMessageDTO emailMessageDTO = getEmailMessage(msgRequest);
        MessageResponse messageResponse = new MessageResponse();
        if (saveMessageAsDraft(emailMessageDTO)) {
            messageResponse.setId("" + emailMessageDTO.getTimestamp());
            messageResponse.mailLocation(MAILBOX_DRAFT);
            messageResponse.setContent(msgRequest);
        }
        return messageResponse;
    }


    private void prepareSearchResponse(Map<String, List<EmailMessageDTO>> mailboxMessages, String mailbox, MessageSearchResponse messageSearchResponse) {
        List<EmailMessageDTO> messageDTOS = mailboxMessages.get(mailbox);
        List<MessageResponse> messageResponses = new ArrayList<>();
        for (EmailMessageDTO emailMessageDTO : messageDTOS) {
            MessageResponse response = new MessageResponse();
            response.setId(""+emailMessageDTO.getTimestamp());
            response.setMailLocation(mailbox);
            MessageRequest messageRequest = new MessageRequest();
            messageRequest.setMessageFrom(getEmailAddress(emailMessageDTO.getMailId()));
            messageRequest.setMessageRecipient(getRecipientDetails(emailMessageDTO));
            messageRequest.setMessageBody(getContentDetails(emailMessageDTO.getSubject(),
                    emailMessageDTO.getContent()));
            response.setContent(messageRequest);
            messageResponses.add(response);
        }
        messageSearchResponse.setMessages(messageResponses);
    }

    private Message getContentDetails(String subject, Map<String, List<String>> content) {
        Message messagePayload = new Message();
        List<MessageContent> contents = new ArrayList<>();
        messagePayload.setSubject(subject);
        for (Map.Entry<String, List<String>> entry : content.entrySet()) {
            MessageContent messageContent = new MessageContent();
            messageContent.setContentType(entry.getKey());
            messageContent.setContentDetails(entry.getValue());
            contents.add(messageContent);
        }
        messagePayload.setContents(contents);
        return messagePayload;
    }

    private RecipientDetail getRecipientDetails(EmailMessageDTO emailMessageDTO) {
        RecipientDetail recipientDetail = new RecipientDetail();
        recipientDetail.setRecipientTo(getEmailAddress(emailMessageDTO.getToIds()));
        recipientDetail.setRecipientCc(getEmailAddress(emailMessageDTO.getCcIds()));
        recipientDetail.setRecipientBcc(getEmailAddress(emailMessageDTO.getBccIds()));
        return recipientDetail;
    }

    private EmailAddress getEmailAddress(String mailId) {
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setId(mailId);
        return emailAddress;
    }

    private List<EmailAddress> getEmailAddress(List<String> mailId) {
        List<EmailAddress> emailAddresses = new ArrayList<>();
        for (String mail : mailId) {
            EmailAddress emailAddress = new EmailAddress();
            emailAddress.setId(mail);
            emailAddresses.add(emailAddress);
        }
        return emailAddresses;
    }

    private EmailMessageDTO getEmailMessage(MessageRequest msgRequest) {
        EmailMessageDTO emailMessageDTO = new EmailMessageDTO();
        emailMessageDTO.setMailId(msgRequest.getMessageFrom().getId());
        emailMessageDTO.setToIds(getEmailIds(msgRequest.getMessageRecipient().getRecipientTo()));
        emailMessageDTO.setCcIds(getEmailIds(msgRequest.getMessageRecipient().getRecipientCc()));
        emailMessageDTO.setBccIds(getEmailIds(msgRequest.getMessageRecipient().getRecipientBcc()));

        emailMessageDTO.setSubject(msgRequest.getMessageBody().getSubject());
        emailMessageDTO.setContent(getContent(msgRequest.getMessageBody().getContents()));
        emailMessageDTO.setTimestamp(System.currentTimeMillis());
        return emailMessageDTO;
    }

    private Map<String, List<String>> getContent(List<MessageContent> contentDetails) {
        Map<String, List<String>> contentMap = new HashMap<>();
        for(MessageContent messageContent : contentDetails) {
            String contentType = messageContent.getContentType();
            // Setting this as the mail can support any type of attachment
            Object object = messageContent.getContentDetails();
            if(contentMap.containsKey(contentType)){
                List<String> content = contentMap.get(contentType);
                content.add(""+object);
            } else {
                List<String> contentList = new ArrayList<>();
                contentList.add(""+object);
                contentMap.put(contentType, contentList);
            }
        }
        return contentMap;
    }

    private List<String> getEmailIds(List<EmailAddress> mailIds) {
        List<String> ids = new ArrayList<>();
        for (EmailAddress emailAddress : mailIds) {
            ids.add(emailAddress.getId());
        }
        return ids;
    }


    public boolean saveMessageAsDraft(EmailMessageDTO emailMessageDTO) {
        boolean isSaved = false;

        // Put mail reference in DRAFT of the sender mailBox
        Map<String, Map<String, List<EmailMessageDTO>>> messages = serviceSenderMailBox(emailMessageDTO,MAILBOX_DRAFT);

        EmailUtil.prettyDisplayMailbox(messages);
        // Check if the usr has mails already. If so add to the list else create a new entry.
        return isSaved;
    }
        /**
         * Adapter for publishing Email to the in-memory storage in a HashMap
         *
         * @param emailMessageDTO
         * @return boolean
         */
    public boolean saveMessage(EmailMessageDTO emailMessageDTO) {
        boolean isSaved = false;

        // Combine to, cc and bcc
        List<String> recipientList = getRecipientList(emailMessageDTO);

        // Put mail reference in SENT of the sender mailBox
        Map<String, Map<String, List<EmailMessageDTO>>> messages = serviceSenderMailBox(emailMessageDTO, MAILBOX_SENT);

        // Put mail reference in INBOX of the receiver mailBox
        isSaved = serviceReceiverMailBox(messages, emailMessageDTO, recipientList, isSaved);

        EmailUtil.prettyDisplayMailbox(messages);
        // Check if the usr has mails already. If so add to the list else create a new entry.
        return isSaved;
    }

    private static boolean serviceReceiverMailBox(Map<String, Map<String, List<EmailMessageDTO>>> messages,
                                                  EmailMessageDTO emailMessageDTO, List<String> recipientList,
                                                  boolean isSaved) {
        // Setting in recipient MailBox
        for (String recipients : recipientList) {
            if (messages.containsKey(recipients)) {
                Map<String, List<EmailMessageDTO>> emailDtos = messages.get(recipients);
                if (emailDtos.containsKey(MAILBOX_INBOX)) {
                    List<EmailMessageDTO> mails = emailDtos.get(MAILBOX_INBOX);
                    mails.add(emailMessageDTO);
                    emailDtos.put(MAILBOX_INBOX, mails);
                } else {
                    List<EmailMessageDTO> mails = new ArrayList<>();
                    mails.add(emailMessageDTO);
                    emailDtos.put(MAILBOX_INBOX, mails);
                }
                messages.put(recipients, emailDtos);
                isSaved = true;
            } else {
                Map<String, List<EmailMessageDTO>> emailDtos = new HashMap<>();
                List<EmailMessageDTO> emailMessageDTOS = new ArrayList<>();
                emailMessageDTOS.add(emailMessageDTO);
                emailDtos.put(MAILBOX_INBOX, emailMessageDTOS);
                messages.put(recipients, emailDtos);
                isSaved = true;
            }
        }
        return isSaved;
    }

    private Map<String, Map<String, List<EmailMessageDTO>>> serviceSenderMailBox(EmailMessageDTO emailMessageDTO,
                                                                                 String mailBoxLocation) {
        // Putting it in Sender MailBox
        Map<String, Map<String, List<EmailMessageDTO>>> messages = emailBoxRepository.getMessages();

        // Check if the user has previous messages if yes add to existing else create new
        if (messages.containsKey(emailMessageDTO.getMailId())) {
            Map<String, List<EmailMessageDTO>> mailBox = messages.get(emailMessageDTO.getMailId());
            if (mailBox.containsKey(mailBoxLocation)) {
                List<EmailMessageDTO> emailDtos = mailBox.get(mailBoxLocation);
                emailDtos.add(emailMessageDTO);
                mailBox.put(mailBoxLocation, emailDtos);
            } else {
                List<EmailMessageDTO> emailDtos = new ArrayList<>();
                emailDtos.add(emailMessageDTO);
                mailBox.put(mailBoxLocation, emailDtos);
            }
        } else {
            Map<String, List<EmailMessageDTO>> mailBox = new HashMap<>();
            List<EmailMessageDTO> emailDtos = new ArrayList<>();
            emailDtos.add(emailMessageDTO);
            mailBox.put(mailBoxLocation, emailDtos);
            messages.put(emailMessageDTO.getMailId(), mailBox);
        }
        return messages;
    }

    // Combine to,cc,bcc Mail addresses
    private static List<String> getRecipientList(EmailMessageDTO emailMessageDTO) {
        List<String> toIds = emailMessageDTO.getToIds();
        List<String> ccIds = emailMessageDTO.getCcIds();
        List<String> bccIds = emailMessageDTO.getBccIds();
        return Stream.of(toIds, ccIds, bccIds)
                .flatMap(Collection::stream).collect(Collectors.toList());
    }
}
