package com.airnz.tpx.commserv.exception;

/**
 *  Custom exception
 */
public class ProcessingFailureException extends RuntimeException {

    public ProcessingFailureException(String errMsg) {
        super(errMsg);
    }

    public ProcessingFailureException(String errMsg, Throwable throwable) {
        super(errMsg, throwable);
    }
}
