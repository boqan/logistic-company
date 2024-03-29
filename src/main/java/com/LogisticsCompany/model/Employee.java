package com.LogisticsCompany.model;


import com.LogisticsCompany.enums.EmployeeType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Represents an employee in the logistics company.
 * This is a base class for various types of employees.
 */
@Entity
@Builder
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

    @Column(name="employee_type", nullable = false, length = 255)
    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;

    @Column(name="salary", nullable = false)
    private double salary;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
              //  ", office=" + office +
                ", name='" + name + '\'' +
                ", employeeType=" + employeeType +
                ", salary=" + salary +
                '}';
    }
}

