package com.LogisticsCompany.error;
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
