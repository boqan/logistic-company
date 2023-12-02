package com.LogisticsCompany.model;

import com.LogisticsCompany.enums.DeliveryStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "office", schema = "transport_company")
public class Office extends IdGenerator {

    @Column(name="address")
    private String address;

    @Column(name="orders")
    @OneToMany(mappedBy = "office")
    private List<Order> orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private LogisticCompany logisticCompany;
}
