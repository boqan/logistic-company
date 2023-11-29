package com.LogisticsCompany.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MappedSuperclass
abstract public class BusinessEntity {

    private BigDecimal revenue;

    private Set<Employee> employees;

    private Set<Client> clients;

    public abstract BigDecimal calculateRevenue();


}
