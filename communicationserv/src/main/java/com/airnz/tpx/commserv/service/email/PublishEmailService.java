package com.airnz.tpx.commserv.service.email;

import com.airnz.tpx.commserv.adapter.MailAdapter;
import com.airnz.tpx.commserv.exception.ProcessingFailureException;
import com.airnz.tpx.commserv.exception.RequestValidationException;
import com.airnz.tpx.commserv.service.AbstractCommunicationService;
import generated.MessageRequest;
import generated.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Publish email service.
 * Have a separate implementation to make the code follow Single responsibility principle.
 */
@Component
@Qualifier("publishEmailService")
public class PublishEmailService extends AbstractCommunicationService<MessageRequest, MessageResponse> {

    @Autowired
    private MailAdapter mailAdapter;

    @Override
    protected void validateRequest(MessageRequest request) throws RequestValidationException {

    }

    @Override
    protected MessageRequest preProcess(MessageRequest request) {
        return request;
    }

    @Override
    protected MessageResponse process(MessageRequest request) throws ProcessingFailureException {
        return mailAdapter.pushMessage(request);
    }

    @Override
    protected MessageResponse postProcess(MessageRequest request, MessageResponse response) {
        return response;
    }
}
