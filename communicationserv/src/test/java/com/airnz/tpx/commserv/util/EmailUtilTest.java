package com.airnz.tpx.commserv.util;

import com.airnz.tpx.commserv.pojo.EmailSearchCriteria;
import com.airnz.tpx.commserv.pojo.MailMessageDTO;
import generated.MessageResponse;
import generated.MessageSearchResponse;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EmailUtilTest {

    @Test
    void testGetFetchCriteria() {
        String authorization = "Bearer token";
        String mailbox = "inbox";
        String page = "1";
        String pageSize = "10";
        String sortOrder = "asc";
        String totalRequired = "true";

        EmailSearchCriteria criteria = EmailUtil.getFetchCriteria(
                authorization, mailbox, page, pageSize, sortOrder, totalRequired);

        assertEquals(authorization, criteria.getAuthorization());
        assertEquals(mailbox, criteria.getMailbox());
        assertEquals(page, criteria.getPage());
        assertEquals(pageSize, criteria.getPageSize());
        assertEquals(sortOrder, criteria.getSortOrder());
        assertEquals(totalRequired, criteria.getTotalRequired());
    }

    @Test
    void testPrettyDisplayMailboxWithMap() {
        Map<String, Map<String, List<MailMessageDTO>>> mailboxMap = new HashMap<>();
        mailboxMap.put("owner1", Collections.singletonMap("inbox", Collections.singletonList(new MailMessageDTO())));

        assertDoesNotThrow(() -> EmailUtil.prettyDisplayMailbox(mailboxMap));
        // You can add more assertions based on your specific expectations
    }

    @Test
    void testPrettyDisplayMailboxWithMessageSearchResponse() {
        MessageSearchResponse messageSearchResponse = new MessageSearchResponse();
        messageSearchResponse.setMessages(Collections.singletonList(new MessageResponse()));

        assertDoesNotThrow(() -> EmailUtil.prettyDisplayMailbox(messageSearchResponse));
        // You can add more assertions based on your specific expectations
    }
}