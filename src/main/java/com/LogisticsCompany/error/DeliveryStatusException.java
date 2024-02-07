package com.LogisticsCompany.error;
/**
 * Exception thrown when an invalid or inappropriate operation related to delivery status occurs.
 * This could include attempting to set a delivery to an invalid status or performing an operation
 * that is not allowed due to the current status of a delivery.
 */
public class DeliveryStatusException extends Throwable {
    public DeliveryStatusException(String s) {
    }
}
