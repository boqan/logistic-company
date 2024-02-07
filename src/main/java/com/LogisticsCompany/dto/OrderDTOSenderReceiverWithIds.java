package com.LogisticsCompany.dto;

import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.enums.DeliveryType;
import lombok.Data;
/**
 * Data Transfer Object (DTO) representing the details of an order within the logistics system.
 * This class is used for transferring order data between different layers of the application,
 * particularly for operations related to creating, updating, and viewing orders.
 * <p>
 * The {@code @Data} annotation from Lombok generates getter and setter methods,
 * {@code toString()}, {@code equals()}, and {@code hashCode()} methods automatically,
 * simplifying the boilerplate code typically associated with Java classes.
 */
@Data
public class OrderDTOSenderReceiverWithIds {
    private Long id;
    private Long sender;
    private Long receiver;
    private double weight;
    private String receiverAddress;
    private double price;
    private DeliveryType deliveryType;
    private DeliveryStatus status;
    private Long officeId;
    public OrderDTOSenderReceiverWithIds() {
    }
    /**
     * Constructs a new OrderDTOSenderReceiverWithIds with specified details.
     *
     * @param id the unique identifier for the order
     * @param sender the ID of the sender
     * @param receiver the ID of the receiver
     * @param weight the weight of the package in kilograms
     * @param receiverAddress the delivery address for the package
     * @param price the price of the delivery service
     * @param deliveryType the type of delivery
     * @param status the current status of the order
     * @param officeId the ID of the handling office
     */
    public OrderDTOSenderReceiverWithIds(Long id, Long sender, Long receiver, double weight, String receiverAddress, double price, DeliveryType deliveryType, DeliveryStatus status, Long officeId) {
        this.id = id;
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
