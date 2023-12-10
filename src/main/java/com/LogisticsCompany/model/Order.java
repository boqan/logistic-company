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
    @JoinColumn(name = "sender_id")
    private Client sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Client receiver;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false, length = 255)
    private String receiverAddress;

    @Column(nullable = false)
    private double price;

    // @Enumerated(EnumType.STRING)
    //private DeliveryType deliveryType;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private Office office;

}