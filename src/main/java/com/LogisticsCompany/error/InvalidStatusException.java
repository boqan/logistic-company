package com.LogisticsCompany.error;
/**
 * Exception indicating that an invalid status has been encountered.
 * This exception can be used to handle scenarios where a status value is not as expected or is not valid
 * within the context of an operation. The exception extends the {@link Exception} class and provides
 * constructors for specifying a message, a cause, or both, allowing developers to provide detailed
 * information about the nature of the invalid status.
 */
public class InvalidStatusException extends Exception{

    public InvalidStatusException() {
        super();
    }

    public InvalidStatusException(String message) {
        super(message);
    }

    public InvalidStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStatusException(Throwable cause) {
        super(cause);
    }

    protected InvalidStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
