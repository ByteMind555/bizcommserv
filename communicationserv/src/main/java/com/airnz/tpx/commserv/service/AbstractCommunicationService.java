package com.airnz.tpx.commserv.service;

import com.airnz.tpx.commserv.exception.ProcessingFailureException;
import com.airnz.tpx.commserv.exception.RequestValidationException;

/**
 * Template pattern for having a sequence of steps which will be common for all different implementation
 *
 * @param <REQ>
 * @param <RESP>
 */
public abstract class AbstractCommunicationService<REQ, RESP> {

    protected abstract void validateRequest(REQ request) throws RequestValidationException;

    protected abstract REQ preProcess(REQ request);

    protected abstract RESP process(REQ request) throws ProcessingFailureException;

    protected abstract RESP postProcess(REQ request, RESP response);

    //Template method for all
    public final RESP doProcess(REQ request) throws
            RequestValidationException, ProcessingFailureException {

        validateRequest(request);

        preProcess(request);

        RESP response = process(request);

        postProcess(request, response);

        return response;
    }

}
