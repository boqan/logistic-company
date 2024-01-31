package com.LogisticsCompany.dto;


import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.model.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends IdGenerator {

    private DeliveryStatus deliveryStatus;
    private BigDecimal price;

    private ClientDTO sender;

    private ClientDTO receiver;
}



