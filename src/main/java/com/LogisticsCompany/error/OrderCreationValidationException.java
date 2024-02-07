package com.LogisticsCompany.error;
/**
 * Exception thrown when the validation of order creation fails. This exception is used
 * to indicate that an attempt to create an order has failed due to validation errors.
 * Validation errors typically involve verifying that the order request contains valid data.
 */
public class OrderCreationValidationException extends Exception {
    public OrderCreationValidationException(String s) {
    }
}
