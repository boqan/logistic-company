package com.LogisticsCompany.error;
/**
 * Exception indicating that an entity being added to the database already exists.
 * This exception is thrown to prevent duplicate entries in the database, ensuring data integrity
 * and uniqueness where required. It extends the {@link Exception} class, providing a constructor
 * that allows for the specification of a detail message to describe the exception.
 */
public class EntityAlreadyExistsInDbException extends Exception {
    public EntityAlreadyExistsInDbException(String s) {
    }
}
