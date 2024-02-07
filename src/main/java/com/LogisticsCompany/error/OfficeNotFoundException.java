package com.LogisticsCompany.error;
/**
 * Exception indicating that an office was not found.
 * This exception can be used to handle scenarios where an attempt to access or retrieve information about
 * an office entity resulted in the office not being found in the system. The exception extends the {@link Exception}
 * class and provides constructors for specifying a message, a cause, or both, allowing developers to provide detailed
 * information about the nature of the office not being found.
 */
public class OfficeNotFoundException extends Exception{

    public OfficeNotFoundException() {
        super();
    }

    public OfficeNotFoundException(String message) {
        super(message);
    }

    public OfficeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OfficeNotFoundException(Throwable cause) {
        super(cause);
    }

    protected OfficeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
