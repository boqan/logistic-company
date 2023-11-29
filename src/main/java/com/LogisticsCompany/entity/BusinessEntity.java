package com.LogisticsCompany.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "business_entity", schema = "transport_company")
abstract public class BusinessEntity {

    @Column(name = "revenue")
    private BigDecimal revenue;

    @Column(name = "employees")
    @OneToMany
    private Set<Employee> employees;

    @Column(name = "clients")
    @OneToMany
    private Set<Client> clients;

    public abstract BigDecimal calculateRevenue();


}
