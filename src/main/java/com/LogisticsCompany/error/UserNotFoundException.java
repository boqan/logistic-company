package com.LogisticsCompany.error;
/**
 * Exception thrown when a user is not found within the system. This exception is used
 * to indicate that an operation or request attempted to access or modify a user that does not exist.
 */
public class UserNotFoundException extends Exception{

        public UserNotFoundException() {
            super();
        }

        public UserNotFoundException(String message) {
            super(message);
        }

        public UserNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }

        public UserNotFoundException(Throwable cause) {
            super(cause);
        }
}
