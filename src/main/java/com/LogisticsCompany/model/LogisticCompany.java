package com.LogisticsCompany.model;

import com.LogisticsCompany.enums.DeliveryStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.query.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "logistic_company")
public class LogisticCompany extends IdGenerator {

    @NotBlank(message = "Company must have a name")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Company must have a country")
    @Column(name = "country")
    private String country;

    @Column(name = "offices")
    @OneToMany(mappedBy = "logisticCompany")
    private List<Office> offices;



}
