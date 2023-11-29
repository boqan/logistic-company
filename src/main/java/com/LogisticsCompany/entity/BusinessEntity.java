package com.LogisticsCompany.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
abstract public class BusinessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "revenue")
    private BigDecimal revenue;


    public abstract BigDecimal calculateRevenue();


}
