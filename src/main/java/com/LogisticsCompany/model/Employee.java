package com.LogisticsCompany.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee extends IdGenerator {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private Office office;

    @NotBlank(message = "Employee should have a name")
    @Column(name="name", nullable = false, length = 255)
    private String name;

    @Column(name="salary", nullable = false)
    private @NotBlank(message = "Employee should have a salary") BigDecimal salary;

}
