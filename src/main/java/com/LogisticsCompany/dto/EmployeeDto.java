package com.LogisticsCompany.dto;


import com.LogisticsCompany.model.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto extends IdGenerator {
    private String name;

}