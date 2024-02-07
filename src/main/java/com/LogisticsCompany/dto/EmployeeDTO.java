package com.LogisticsCompany.dto;

import com.LogisticsCompany.enums.EmployeeType;
import com.LogisticsCompany.model.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Represents the data transfer object (DTO) for an employee. This class is used to transport employee data
 * between processes, specifically for transferring employee-related information to and from the client layer
 * and the server. Inherits a unique identifier from {@code IdGenerator} to uniquely identify each employee instance.
 * <p>
 * Annotations:
 * <ul>
 *     <li>{@code @Data} - Generates boilerplate code such as getters, setters, {@code toString()}, {@code equals()},
 *     and {@code hashCode()} methods automatically.</li>
 *     <li>{@code @Builder} - Implements the Builder pattern for easier construction of {@code EmployeeDTO} objects.</li>
 *     <li>{@code @AllArgsConstructor} - Generates a constructor with parameters for all fields present in the class.</li>
 *     <li>{@code @NoArgsConstructor} - Generates a no-argument constructor, required for frameworks and libraries
 *     that instantiate objects via reflection.</li>
 * </ul>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO extends IdGenerator {

    private String name;
    private double salary;
    private Long officeID;
    private EmployeeType employeeType;

}