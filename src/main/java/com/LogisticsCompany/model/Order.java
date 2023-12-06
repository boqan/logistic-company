package com.LogisticsCompany.model;

import com.LogisticsCompany.enums.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "my_order") // The name "order" is a reserved keyword in SQL that is why we need to change it
public class Order extends IdGenerator{

    @ManyToOne(fetch = FetchType.LAZY)
    private Office office;
    @Enumerated
    @Column(name = "delivery_status")
    private DeliveryStatus deliveryStatus;

    @Column(name = "price")
    private BigDecimal price;
}
