package com.airnz.tpx.commserv.controller;

import com.airnz.tpx.commserv.exception.ProcessingFailureException;
import com.airnz.tpx.commserv.exception.RequestValidationException;
import com.airnz.tpx.commserv.pojo.EmailSearchCriteria;
import com.airnz.tpx.commserv.service.AbstractCommunicationService;
import generated.MessageRequest;
import generated.MessageResponse;
import generated.MessageSearchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommunicationServControllerTest {

    @Mock
    private AbstractCommunicationService<EmailSearchCriteria, MessageSearchResponse> fetchEmailService;

    @Mock
    private AbstractCommunicationService<MessageRequest, MessageResponse> publishEmailService;

    @Mock
    private AbstractCommunicationService<MessageRequest, MessageResponse> draftEmailService;

    @InjectMocks
    private CommunicationServController communicationServController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testPostEmail() throws RequestValidationException, ProcessingFailureException {
        MessageRequest messageRequest = new MessageRequest();
        when(publishEmailService.doProcess(any(MessageRequest.class))).thenReturn(new MessageResponse());

        ResponseEntity responseEntity = communicationServController.postEmail(messageRequest);

        verify(publishEmailService, times(1)).doProcess(any(MessageRequest.class));
        // Add assertions based on your expectations
    }

    @Test
    void testDraftEmail() throws RequestValidationException, ProcessingFailureException {
        MessageRequest messageRequest = new MessageRequest();
        when(draftEmailService.doProcess(any(MessageRequest.class))).thenReturn(new MessageResponse());

        ResponseEntity responseEntity = communicationServController.draftEmail(messageRequest);

        verify(draftEmailService, times(1)).doProcess(any(MessageRequest.class));
        // Add assertions based on your expectations
    }

    @Test
    void testGetEmails() throws RequestValidationException, ProcessingFailureException {
        when(fetchEmailService.doProcess(any(EmailSearchCriteria.class))).thenReturn(new MessageSearchResponse());

        ResponseEntity responseEntity = communicationServController.getEmails(
                "Authorization", "mailbox", "page", "pageSize", "sortOrder", "totalRequired"
        );

        verify(fetchEmailService, times(1)).doProcess(any(EmailSearchCriteria.class));
        // Add assertions based on your expectations
    }
}

