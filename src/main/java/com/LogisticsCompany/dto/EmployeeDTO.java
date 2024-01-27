package com.LogisticsCompany.dto;

import com.LogisticsCompany.enums.EmployeeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private String name;
    private double salary;
    private Long officeID;
    private EmployeeType employeeType;
}