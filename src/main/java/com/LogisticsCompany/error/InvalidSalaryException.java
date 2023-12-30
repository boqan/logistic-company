package com.LogisticsCompany.error;

public class InvalidSalaryException extends Exception {
    public InvalidSalaryException() {
        super();
    }
    public InvalidSalaryException(String message) {
        super(message);
    }

    public InvalidSalaryException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSalaryException(Throwable cause) {
        super(cause);
    }

    protected InvalidSalaryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
