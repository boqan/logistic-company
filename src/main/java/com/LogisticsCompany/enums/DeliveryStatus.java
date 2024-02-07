package com.LogisticsCompany.enums;
/**
 * Enumerates the possible statuses of a delivery within the logistics system.
 * Defines three states: PENDING, DELIVERED, and CANCELLED, each representing a distinct phase
 * in the delivery lifecycle.
 */
public enum DeliveryStatus {
    PENDING, DELIVERED, CANCELLED;
    /**
     * Checks if a given string matches any of the defined delivery statuses.
     *
     * @param status The status string to check against the enum values.
     * @return {@code true} if the specified status matches any of the enum values, {@code false} otherwise.
     */
    public static boolean isValidStatus(String status) {
        for (DeliveryStatus deliveryStatus : DeliveryStatus.values()) {
            if (deliveryStatus.name().equals(status)) {
                return true;
            }
        }
        return false;
    }
}
