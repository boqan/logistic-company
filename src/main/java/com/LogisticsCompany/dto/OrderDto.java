package com.LogisticsCompany.dto;


import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.model.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
/**
 * Data Transfer Object (DTO) for orders within the system.
 * Extends {@link IdGenerator} to inherit unique identifier generation capabilities.
 * This class holds information about an order, including its delivery status, price,
 * and details about the sender and receiver.
 *
 * The use of Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor) automatically
 * generates the necessary boilerplate code such as getters, setters, constructors,
 * and other commonly used methods.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends IdGenerator {

    private DeliveryStatus deliveryStatus;
    private double price;

    private ClientDTO sender;

    private ClientDTO receiver;
}



