package com.LogisticsCompany.dto;

import com.LogisticsCompany.enums.EmployeeType;
import com.LogisticsCompany.model.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO extends IdGenerator {

    private String name;
    private double salary;
    private Long officeID;
    private EmployeeType employeeType;

}