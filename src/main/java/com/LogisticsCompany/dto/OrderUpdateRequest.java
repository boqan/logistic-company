package com.LogisticsCompany.dto;

import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.enums.DeliveryType;
import lombok.Data;
/**
 * Represents a request to update an existing order with new information.
 * This class encapsulates all possible fields that can be updated in an order,
 * including sender, receiver, weight, receiver address, price, delivery type, status, and the associated office ID.
 *
 * The use of the Lombok @Data annotation automatically generates getters, setters,
 * and methods for toString, equals, and hashCode. Additionally, manual constructors are provided
 * to allow for flexibility in object creation, supporting both no-argument and all-argument initialization.
 */
@Data
public class OrderUpdateRequest {
    private Long sender;
    private Long receiver;
    private double weight;
    private String receiverAddress;
    private double price;
    private DeliveryType deliveryType;
    private DeliveryStatus status;
    private Long officeId;

    // Add constructors, getters, and setters if not using Lombok
    public OrderUpdateRequest() {
    }
    /**
     * All-argument constructor for creating an instance with initialized fields.
     *
     * @param sender ID of the sender
     * @param receiver ID of the receiver
     * @param weight Weight of the order
     * @param receiverAddress Address of the receiver
     * @param price Price of the order
     * @param deliveryType Type of delivery
     * @param status Current status of the order
     * @param officeId ID of the office handling the order
     */
    public OrderUpdateRequest(Long sender, Long receiver, double weight, String receiverAddress, double price, DeliveryType deliveryType, DeliveryStatus status, Long officeId) {
        this.sender = sender;
        this.receiver = receiver;
        this.weight = weight;
        this.receiverAddress = receiverAddress;
        this.price = price;
        this.deliveryType = deliveryType;
        this.status = status;
        this.officeId = officeId;
    }
}
