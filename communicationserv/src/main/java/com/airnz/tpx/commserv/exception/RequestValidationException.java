package com.airnz.tpx.commserv.exception;

public class RequestValidationException extends RuntimeException {

    public RequestValidationException(String errMsg) {
        super(errMsg);
    }

    public RequestValidationException(String errMsg, Throwable throwable) {
        super(errMsg, throwable);
    }
}
