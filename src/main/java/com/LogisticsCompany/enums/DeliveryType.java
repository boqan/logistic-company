package com.LogisticsCompany.enums;
/**
 * Enum representing the available types of delivery methods in the system.
 * This categorization helps in specifying the delivery preferences for orders
 * and in streamlining logistics operations.
 *
 * - TO_OFFICE: Indicates that the delivery is to be made to an office location. This option is often used
 *              for centralized pickup points or when delivering to business addresses.
 * - TO_HOME_ADDRESS: Indicates that the delivery is to be made directly to a home address. This option caters
 *                    to individual recipients and provides the convenience of door-to-door service.
 */
public enum DeliveryType {
    TO_OFFICE,
    TO_HOME_ADDRESS;
}

