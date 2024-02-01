package com.LogisticsCompany.dto;

public record OrderCreationRequest(
        Long office,
        Long sender,
        Long receiver,
        double weight,
        String receiverAddress,
        String deliveryType) {
}
