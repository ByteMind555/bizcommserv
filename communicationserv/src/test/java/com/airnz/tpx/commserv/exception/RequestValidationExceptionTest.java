package com.airnz.tpx.commserv.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RequestValidationExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String errorMessage = "Invalid request data.";
        RequestValidationException exception = new RequestValidationException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithMessageAndThrowable() {
        String errorMessage = "Invalid request data.";
        Throwable cause = new RuntimeException("Root cause exception");
        RequestValidationException exception = new RequestValidationException(errorMessage, cause);

        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
