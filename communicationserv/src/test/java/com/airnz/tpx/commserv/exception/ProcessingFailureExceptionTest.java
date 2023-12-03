package com.airnz.tpx.commserv.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProcessingFailureExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String errorMessage = "An error occurred during processing.";
        ProcessingFailureException exception = new ProcessingFailureException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithMessageAndThrowable() {
        String errorMessage = "An error occurred during processing.";
        Throwable cause = new RuntimeException("Root cause exception");
        ProcessingFailureException exception = new ProcessingFailureException(errorMessage, cause);

        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
