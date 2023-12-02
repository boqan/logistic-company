package com.LogisticsCompany.error;


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
