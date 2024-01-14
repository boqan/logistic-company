package com.LogisticsCompany.dto;

import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.enums.DeliveryType;
import lombok.Data;

@Data
public class OrderDTOnoOfficeSenderRecieverWithIds {
    private Long id;
    private Long sender;
    private Long receiver;
    private double weight;
    private String receiverAddress;
    private double price;
    private DeliveryType deliveryType;
    private DeliveryStatus status;
    public OrderDTOnoOfficeSenderRecieverWithIds() {
    }

    public OrderDTOnoOfficeSenderRecieverWithIds(Long id, Long sender, Long receiver, double weight, String receiverAddress, double price, DeliveryType deliveryType, DeliveryStatus status) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.weight = weight;
        this.receiverAddress = receiverAddress;
        this.price = price;
        this.deliveryType = deliveryType;
        this.status = status;
    }
}
