package com.LogisticsCompany.dto;

import com.LogisticsCompany.model.IdGenerator;
import com.LogisticsCompany.model.Office;
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
}