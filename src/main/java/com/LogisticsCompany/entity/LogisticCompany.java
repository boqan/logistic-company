package com.LogisticsCompany.entity;

import com.LogisticsCompany.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.query.Order;

import java.math.BigDecimal;
import java.util.Map;


import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Table(name = "logistic_company")
public class LogisticCompany extends BusinessEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "offices")
    @OneToMany(mappedBy = "logisticCompany")
    private Set<Office> offices;

    @OneToMany(mappedBy = "logisticCompany" )
    private Set<Employee> employees;


    @OneToMany(mappedBy = "logisticCompany" )
    private Set<Client> clients;

    @Column(name = "country")
    private String country;

    public Map<Office, Set<Order>> getOrders() {
        return null;
    }

    public Map<Integer, Set<Order>> getOrders(DeliveryStatus status) {
        return null;
    }

    @Override
    public BigDecimal calculateRevenue() {
        return null;
    }
}
