package com.LogisticsCompany.dto;

import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.enums.DeliveryType;
import lombok.Data;

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
