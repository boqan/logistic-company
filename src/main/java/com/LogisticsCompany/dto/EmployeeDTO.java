package com.LogisticsCompany.dto;

import com.LogisticsCompany.enums.EmployeeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private String name;
    private BigDecimal salary;
    private Long officeID;
    private EmployeeType employeeType;
}