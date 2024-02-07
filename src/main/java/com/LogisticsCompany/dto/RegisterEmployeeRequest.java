package com.LogisticsCompany.dto;

import com.LogisticsCompany.enums.AccountType;
import com.LogisticsCompany.enums.EmployeeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Represents a request to register a new employee within the system. This class encapsulates all
 * necessary information for creating an employee's account, including their personal details,
 * employment information (such as salary and employee type), account credentials, and associated office.
 * <p>
 * Annotations:
 * <ul>
 *     <li>{@code @Data} - Lombok annotation to automatically generate getter and setter methods,
 *     {@code toString()}, {@code equals()}, and {@code hashCode()} methods.</li>
 *     <li>{@code @AllArgsConstructor} - Generates a constructor with arguments for all fields.</li>
 *     <li>{@code @NoArgsConstructor} - Generates a no-argument constructor, facilitating serialization/deserialization
 *     and other default constructions.</li>
 * </ul>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterEmployeeRequest {

    private String name;

    private double salary;

    private Long officeID;

    private EmployeeType employeeType;

    private String username;

    private String password;

    private String email;

    private AccountType role;

    private Long connectedId;
}
