package com.LogisticsCompany.dto;

import com.LogisticsCompany.enums.AccountType;
import com.LogisticsCompany.enums.EmployeeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
