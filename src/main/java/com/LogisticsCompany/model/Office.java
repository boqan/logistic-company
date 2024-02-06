package com.LogisticsCompany.model;

import com.LogisticsCompany.enums.DeliveryStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "office", schema = "transport_company")
public class Office extends BusinessEntity {

    @Column(name="address", nullable = false, length = 255)
    private String address;

    @Column(name="orders", nullable = false, length = 255)
    @OneToMany(mappedBy = "office")
    private List<Order> orders = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private LogisticCompany logisticCompany;
}
