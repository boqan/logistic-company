package com.LogisticsCompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends IdGenerator {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private Office office;

    @NotBlank(message = "Employee should have a name")
    @Column(name="name", nullable = false, length = 255)
    private String name;

    @NotBlank(message = "Employee should have a salary")
    @Column(name="salary", nullable = false)
    private double salary;

}
