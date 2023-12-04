package com.airnz.tpx.commserv.util;

import com.airnz.tpx.commserv.pojo.EmailSearchCriteria;
import com.airnz.tpx.commserv.pojo.MailMessageDTO;
import generated.MessageResponse;
import generated.MessageSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Helper class for Email service
 */
public class EmailUtil {

    private static Logger LOG = LoggerFactory.getLogger(EmailUtil.class);

    /**
     *
     * @param authorization
     * @param mailbox
     * @param page
     * @param pageSize
     * @param sortOrder
     * @param totalRequired
     * @return
     */
    public static EmailSearchCriteria getFetchCriteria(String authorization, String mailbox,
                                                       String page, String pageSize,
                                                       String sortOrder, String totalRequired) {
        return new EmailSearchCriteria(authorization, mailbox,
                page, pageSize, sortOrder, totalRequired);
    }

    /**
     *
     * @param emailSearchCriteria
     * @return
     */
    public static String getUserId(EmailSearchCriteria emailSearchCriteria) {
        String authorization = emailSearchCriteria.getAuthorization();
        String userId = "";
        if(authorization !=null && !authorization.isEmpty()) {
            userId = authorization.split(" ")[1];
        }
        return userId;
    }

    /**
     *
     * @param inMemoryMailbox
     */
    public static void prettyDisplayMailbox(Map<String, Map<String, List<MailMessageDTO>>> inMemoryMailbox) {
        LOG.info("------------------------------------------------------------------------");
        for (Map.Entry<String, Map<String, List<MailMessageDTO>>> pair : inMemoryMailbox.entrySet()) {
            String key = pair.getKey();
            Map<String, List<MailMessageDTO>> value = pair.getValue();
            LOG.info("Owner: " + key);
            for (Map.Entry<String, List<MailMessageDTO>> stringListEntry : value.entrySet()) {
                LOG.info("MailBox:" + stringListEntry.getKey());
                for(MailMessageDTO emailMessageDTOS : stringListEntry.getValue()){
                    LOG.info("Mails:" + emailMessageDTOS);
                }
            }
        }
        LOG.info("------------------------------------------------------------------------");
    }

    public static void prettyDisplayMailbox(MessageSearchResponse messageSearchResponse) {
        List<MessageResponse> messages = messageSearchResponse.getMessages();
        LOG.info("------------------------------------------------------------------------");
        for(MessageResponse messageResponse : messages) {
            LOG.info("Payload: "+ messageResponse);
        }
        LOG.info("------------------------------------------------------------------------");
    }
}
