package com.airnz.tpx.commserv.testutils;

import generated.*;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static MessageRequest getMessageRequest(String senderEmail, String toEmail,
                                                   String ccEmail, String mailSubject, String mailContentType, String mailContent) {
        MessageRequest request = new MessageRequest();
        EmailAddress senderEmailAddress = new EmailAddress();
        senderEmailAddress.setId(senderEmail);
        request.setMessageFrom(senderEmailAddress);
        RecipientDetail recipientDetail = new RecipientDetail();

        List<EmailAddress> toEmails = new ArrayList<>();
        EmailAddress toEmailAddress = new EmailAddress();
        toEmailAddress.setId(toEmail);
        toEmails.add(toEmailAddress);
        recipientDetail.setRecipientTo(toEmails);

        List<EmailAddress> ccEmails = new ArrayList<>();
        EmailAddress ccEmailAddress = new EmailAddress();
        ccEmailAddress.setId(ccEmail);
        ccEmails.add(ccEmailAddress);
        recipientDetail.setRecipientCc(ccEmails);

        request.setMessageRecipient(recipientDetail);

        Message messagePayload = new Message();
        messagePayload.setSubject(mailSubject);
        List<MessageContent> contents = new ArrayList<>();
        MessageContent messageContent = new MessageContent();
        messageContent.setContentType(mailContentType);
        messageContent.setContentDetails(mailContent);
        contents.add(messageContent);
        messagePayload.setContents(contents);
        request.setMessageBody(messagePayload);
        return request;
    }
}
