package com.LogisticsCompany.controller;

import com.LogisticsCompany.model.Employee;
import com.LogisticsCompany.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

//    public Employee saveEmployee(@Valid @RequestBody Employee employee){
//        return employeeService.saveEmployee(employee);
//    }
}
