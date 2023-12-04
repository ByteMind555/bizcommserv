package com.airnz.tpx.commserv.data;

import com.airnz.tpx.commserv.pojo.MailMessageDTO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This ideally should be a persistence layer or
 * for a Mailbox like a Message broker layer we can invoke from the service.
 */
@Repository
public class MailRepository {

    // Collection structure is as follows <mailId, <status, List<Mails>>>
    private static Map<String, Map<String, List<MailMessageDTO>>> IN_MEMORY_MAILBOX = new HashMap<>();

    public Map<String, Map<String, List<MailMessageDTO>>> getMessages() {
        //TODO need to do it more securely.
        // This code is vulnerable as the Map data can be modified.
        return IN_MEMORY_MAILBOX;
    }
}
