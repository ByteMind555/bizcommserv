package com.airnz.tpx.commserv.service.email;

import com.airnz.tpx.commserv.adapter.email.EMailboxAdapter;
import com.airnz.tpx.commserv.exception.ProcessingFailureException;
import com.airnz.tpx.commserv.exception.RequestValidationException;
import com.airnz.tpx.commserv.pojo.EmailSearchCriteria;
import com.airnz.tpx.commserv.service.AbstractCommunicationService;
import generated.MessageSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("fetchEmailService")
public class FetchEmailService extends AbstractCommunicationService<EmailSearchCriteria, MessageSearchResponse> {

    @Autowired
    private EMailboxAdapter eMailboxAdapter;

    @Override
    protected void validateRequest(EmailSearchCriteria request) throws RequestValidationException {

    }

    @Override
    protected EmailSearchCriteria preProcess(EmailSearchCriteria request) {
        return request;
    }

    @Override
    protected MessageSearchResponse process(EmailSearchCriteria request) throws ProcessingFailureException {
        return eMailboxAdapter.getMessages(request);
    }

    @Override
    protected MessageSearchResponse postProcess(EmailSearchCriteria request, MessageSearchResponse response) {
        return response;
    }
}
