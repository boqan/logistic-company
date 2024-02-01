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
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "logistic_company")
public class LogisticCompany extends IdGenerator {

    @NotBlank(message = "Company must have a name")
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @NotBlank(message = "Company must have a country")
    @Column(name = "country", nullable = false, length = 255)
    private String country;

    @Column(name = "offices")
    @OneToMany(mappedBy = "logisticCompany")
    private List<Office> offices;



}
