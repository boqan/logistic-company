package com.LogisticsCompany.model;

import com.LogisticsCompany.enums.DeliveryStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.query.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import java.util.Set;
/**
 * Represents a logistics company.
 * This class extends the IdGenerator class and includes information about the company's name,
 * country, and a list of offices associated with the company.
 */
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
    @Builder.Default
    private List<Office> offices = new ArrayList<>();



}
