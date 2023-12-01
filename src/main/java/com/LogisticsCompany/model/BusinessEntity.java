package com.LogisticsCompany.model;

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
public class BusinessEntity extends IdGenerator {

    @Column(name = "revenue")
    private BigDecimal revenue;

    @OneToMany(mappedBy = "logisticCompany" )
    private Set<Employee> employees;

    @OneToMany(mappedBy = "logisticCompany" )
    private Set<Client> clients;
}
