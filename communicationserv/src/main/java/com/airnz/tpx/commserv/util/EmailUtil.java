package com.airnz.tpx.commserv.util;

import com.airnz.tpx.commserv.pojo.EmailMessageDTO;
import com.airnz.tpx.commserv.pojo.EmailSearchCriteria;
import generated.MessageResponse;
import generated.MessageSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class EmailUtil {

    private static Logger LOG = LoggerFactory.getLogger(EmailUtil.class);

    public static EmailSearchCriteria getFetchCriteria(String authorization, String mailbox,
                                                       String page, String pageSize,
                                                       String sortOrder, String totalRequired) {
        return new EmailSearchCriteria(authorization, mailbox,
                page, pageSize, sortOrder, totalRequired);
    }

    public static void prettyDisplayMailbox(Map<String, Map<String, List<EmailMessageDTO>>> inMemoryMailbox) {
        LOG.info("------------------------------------------------------------------------");
        for (Map.Entry<String, Map<String, List<EmailMessageDTO>>> pair : inMemoryMailbox.entrySet()) {
            String key = pair.getKey();
            Map<String, List<EmailMessageDTO>> value = pair.getValue();
            LOG.info("Owner: " + key);

            for (Map.Entry<String, List<EmailMessageDTO>> stringListEntry : value.entrySet()) {
                LOG.info("MailBox:" + stringListEntry.getKey());
                for(EmailMessageDTO emailMessageDTOS : stringListEntry.getValue()){
                    LOG.info("Mails:" + emailMessageDTOS);
                }
            }
        }
        LOG.info("------------------------------------------------------------------------");
    }

    public static void prettyDisplayMailbox(MessageSearchResponse messageSearchResponse) {
        List<MessageResponse> messages = messageSearchResponse.getMessages();
        System.out.println("------------------------------------------------------------------------");
        for(MessageResponse messageResponse : messages) {
            System.out.println(messageResponse);
        }
        System.out.println("------------------------------------------------------------------------");
    }
}
