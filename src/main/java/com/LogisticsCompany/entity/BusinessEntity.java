package com.LogisticsCompany.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "business_entity", schema = "transport_company")
abstract public class BusinessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "revenue")
    private BigDecimal revenue;

    @Column(name = "employees")
    @OneToMany(mappedBy = "businessEntity")
    private Set<Employee> employees;

    @Column(name = "clients")
    @OneToMany(mappedBy = "businessEntity")
    private Set<Client> clients;

    public abstract BigDecimal calculateRevenue();


}
