package com.airnz.tpx.commserv.data;

import com.airnz.tpx.commserv.pojo.EmailMessageDTO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmailRepository {

    // <mailId, <status, List<Mails>>>
    private static Map<String, Map<String, List<EmailMessageDTO>>> IN_MEMORY_MAILBOX = new HashMap<>();

    public Map<String, Map<String, List<EmailMessageDTO>>> getMessages() {
        //TODO need to do it more securely.
        // This code is vulnerable as the Map data can be modified.
        return IN_MEMORY_MAILBOX;
    }
}
