package com.LogisticsCompany.error;

public class InvalidDTOException extends Exception {
    public InvalidDTOException() {
        super();
    }
    public InvalidDTOException(String message) {
        super(message);
    }

    public InvalidDTOException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDTOException(Throwable cause) {
        super(cause);
    }

    protected InvalidDTOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}