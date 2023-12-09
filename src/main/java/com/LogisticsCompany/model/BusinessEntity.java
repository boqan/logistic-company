package com.LogisticsCompany.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessEntity extends IdGenerator {

    @Column(name = "revenue")
    private BigDecimal revenue=BigDecimal.ZERO;

    @OneToMany(mappedBy = "office" )
    private List<Employee> employees;

    @OneToMany(mappedBy = "office" )
    private List<Client> clients;
}
