package com.LogisticsCompany.error;
/**
 * Exception indicating that a Data Transfer Object (DTO) is invalid.
 * This could arise from various scenarios, such as failed validation checks, missing required fields,
 * or data format issues. The exception extends {@link Exception}, allowing it to be used within
 * a broad range of application layers, especially service and controller layers where DTOs are commonly used.
 *
 * Provides constructors for specifying a message, a cause, or both, and also allows for the
 * suppression of stack trace and cause information in scenarios where detailed error reporting
 * is not desired.
 */
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
