package com.LogisticsCompany.error;

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
