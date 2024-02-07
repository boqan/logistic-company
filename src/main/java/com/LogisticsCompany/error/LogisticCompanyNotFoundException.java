package com.LogisticsCompany.error;

/**
 * Exception thrown when a logistic company is not found within the system. This exception is used
 * to indicate that an operation or request attempted to access or modify a logistic company that does not exist.
 */
public class LogisticCompanyNotFoundException extends Exception{
    public LogisticCompanyNotFoundException() {
        super();
    }

    public LogisticCompanyNotFoundException(String message) {
        super(message);
    }

    public LogisticCompanyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogisticCompanyNotFoundException(Throwable cause) {
        super(cause);
    }

    protected LogisticCompanyNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
