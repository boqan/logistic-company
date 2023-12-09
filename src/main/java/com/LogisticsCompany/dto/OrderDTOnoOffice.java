package com.LogisticsCompany.dto;


import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.model.Client;
import com.LogisticsCompany.model.IdGenerator;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTOnoOffice extends IdGenerator {

    private DeliveryStatus deliveryStatus;
    private BigDecimal price;

    private ClientDTOnoOffice sender;

    private ClientDTOnoOffice receiver;
}



