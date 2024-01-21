package com.LogisticsCompany.model;

import com.LogisticsCompany.enums.EmployeeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee extends IdGenerator {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private Office office;

    @Column(name="name", nullable = false, length = 255)
    private String name;

    @Column(name="employee_type", nullable = false, length = 255)
    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;

    @Column(name="salary", nullable = false)
    private BigDecimal salary;

}
