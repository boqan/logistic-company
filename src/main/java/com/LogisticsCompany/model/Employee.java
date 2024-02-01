package com.LogisticsCompany.model;

import com.LogisticsCompany.enums.EmployeeType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", unique = false)
    private Office office;

    @Column(name="name", nullable = false, length = 255)
    private String name;

    @Column(name="email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name="employee_type", nullable = false, length = 255)
    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;

    @Column(name="salary", nullable = false)
    private double salary;

}