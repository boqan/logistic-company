package com.LogisticsCompany.model;

import com.LogisticsCompany.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "office", schema = "transport_company")
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name="address")
    private String address;

    @Column(name="orders")
    @OneToMany(mappedBy = "office")
    private Set<Order> orders;

    @ManyToOne(fetch = FetchType.LAZY)
    private LogisticCompany logisticCompany;

    public Set<Order> getOrders(DeliveryStatus status) {
        return null;
    }

    public Set<Order> getOrders(DeliveryStatus status, Client client) {
        return null;
    }
}
