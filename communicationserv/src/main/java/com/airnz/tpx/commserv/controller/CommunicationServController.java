package com.airnz.tpx.commserv.controller;

import com.airnz.tpx.commserv.exception.ProcessingFailureException;
import com.airnz.tpx.commserv.exception.RequestValidationException;
import com.airnz.tpx.commserv.pojo.EmailSearchCriteria;
import com.airnz.tpx.commserv.service.AbstractCommunicationService;
import com.airnz.tpx.commserv.util.EmailUtil;
import generated.MessageRequest;
import generated.MessageResponse;
import generated.MessageSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommunicationServController {

    private static Logger LOG = LoggerFactory.getLogger(CommunicationServController.class);

    @Autowired
    @Qualifier("fetchEmailService")
    private AbstractCommunicationService<EmailSearchCriteria, MessageSearchResponse> fetchEmailService;

    @Autowired
    @Qualifier("publishEmailService")
    private AbstractCommunicationService<MessageRequest, MessageResponse> publishEmailService;

    @Autowired
    @Qualifier("draftEmailService")
    private AbstractCommunicationService<MessageRequest, MessageResponse> draftEmailService;

    @PostMapping("/mails/send")
    public ResponseEntity postEmail(@RequestBody MessageRequest messageRequest) {
        try {
            MessageResponse messageResponse = publishEmailService.doProcess(messageRequest);
            return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(messageResponse);
        } catch (RequestValidationException ve) {
            String msg = "Unexpected exception encountered while send EMail. Request: " +messageRequest;
            LOG.error(msg, ve);
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(ve);
        } catch (ProcessingFailureException pe) {
            String msg = "Unexpected exception encountered while send EMail. Request: " +messageRequest;
            LOG.error(msg, pe);
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(pe);
        }
    }

    @PostMapping("/mails/drafts")
    public ResponseEntity draftEmail(@RequestBody MessageRequest messageRequest) {
        try {
            MessageResponse messageResponse = draftEmailService.doProcess(messageRequest);
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(messageResponse);
        } catch (RequestValidationException ve) {
            String msg = "Unexpected exception encountered while send EMail. Request: " +messageRequest;
            LOG.error(msg, ve);
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(ve);
        } catch (ProcessingFailureException pe) {
            String msg = "Unexpected exception encountered while send EMail. Request: " +messageRequest;
            LOG.error(msg, pe);
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(pe);
        }
    }

    @GetMapping("/mails/search")
    public ResponseEntity getEmails(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                           @RequestHeader(value="mailbox", required = true) String mailbox,
                                           @RequestHeader(value="page", required = false) String page,
                                           @RequestHeader(value="page_size", required = false) String pageSize,
                                           @RequestHeader(value="sort_order", required = false) String sortOrder,
                      @RequestHeader(value="total_required", required = false) String totalRequired) {
        EmailSearchCriteria emailSearchCriteria = EmailUtil.getFetchCriteria(authorization, mailbox, page,
                pageSize, sortOrder, totalRequired);
        MessageSearchResponse messageSearchResponse = new MessageSearchResponse();
        try {
            messageSearchResponse = fetchEmailService.doProcess(emailSearchCriteria);
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(messageSearchResponse);
        } catch (RequestValidationException ve) {
            String msg = "Unexpected exception encountered invoking getEmails. Request: " +emailSearchCriteria;
            LOG.error(msg, ve);
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(ve);
        }
        catch (ProcessingFailureException pe) {
            String msg = "Unexpected exception encountered invoking getEmails. Request: " +emailSearchCriteria;
            LOG.error(msg, pe);
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(pe);
        }
    }
}


