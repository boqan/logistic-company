package com.LogisticsCompany.dto;

import com.LogisticsCompany.enums.EmployeeType;
import com.LogisticsCompany.model.IdGenerator;
import com.LogisticsCompany.model.Office;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO extends IdGenerator {
    private String name;
    private BigDecimal salary;
    private Long officeID;
    private EmployeeType employeeType;
}