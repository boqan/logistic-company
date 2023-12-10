package com.LogisticsCompany.enums;

public enum DeliveryStatus {
    PENDING, DELIVERED, CANCELLED;

    public static boolean isValidStatus(String status) {
        for (DeliveryStatus deliveryStatus : DeliveryStatus.values()) {
            if (deliveryStatus.name().equals(status)) {
                return true;
            }
        }
        return false;
    }
}
