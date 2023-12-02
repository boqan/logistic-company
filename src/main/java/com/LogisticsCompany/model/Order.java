package com.LogisticsCompany.model;

import com.LogisticsCompany.service.OrderService;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

//@Builder
//@AllArgsConstructor
//public class Order {
//    private final double weight;
//    private final Client sender;
//    private final Client receiver;
//    private final String receiverAddress;
//
//    private final double price;
//
//    private final DeliveryType deliveryType;
//
//    private final DeliveryStatus status;
//
//    private final Office office;
//
//    private OrderService orderService;
//}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
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

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private Office office;

    // Additional methods such as calculatePrice() can be added here if needed
}
