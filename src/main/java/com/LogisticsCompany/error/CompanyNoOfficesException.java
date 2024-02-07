package com.LogisticsCompany.error;
/**
 * Custom exception class to indicate that a company does not have any offices.
 * This exception can be thrown in scenarios where operations require a company to have
 * at least one office, but none are found. It extends the standard {@link Exception} class,
 * allowing for optional inclusion of a message and a cause.
 */
public class CompanyNoOfficesException extends Exception {

    public CompanyNoOfficesException() {
        super();
    }

    public CompanyNoOfficesException(String message) {
        super(message);
    }

    public CompanyNoOfficesException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompanyNoOfficesException(Throwable cause) {
        super(cause);
    }

    protected CompanyNoOfficesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
