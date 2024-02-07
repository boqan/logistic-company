package com.LogisticsCompany.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/**
 * A base class representing a business entity with revenue, employees, and clients.
 * This class is annotated with @MappedSuperclass to indicate that it is not an entity on its own,
 * but its fields and mappings will be inherited by subclasses.
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessEntity extends IdGenerator {

    @Column(name = "revenue", nullable = false)
    private BigDecimal revenue=BigDecimal.ZERO;

    @OneToMany(mappedBy = "office")
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(mappedBy = "office")
    private List<Client> clients = new ArrayList<>();
}
