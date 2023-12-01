package com.LogisticsCompany.error;

public class BusinessEntityNotFoundException extends Exception{
    public BusinessEntityNotFoundException() {
        super();
    }

    public BusinessEntityNotFoundException(String message) {
        super(message);
    }

    public BusinessEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessEntityNotFoundException(Throwable cause) {
        super(cause);
    }

    protected BusinessEntityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
