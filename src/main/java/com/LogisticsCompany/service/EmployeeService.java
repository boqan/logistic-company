package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.error.InvalidSalaryException;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createOrUpdateEmployee(Long id, EmployeeDTO employeeDTO) throws InvalidSalaryException;
    void deleteEmployeeById(Long id);
    EmployeeDTO findEmployeeById(Long id);
    EmployeeDTO findEmployeeByName(String name);
    List<EmployeeDTO> getAllEmployees();
    List<EmployeeDTO> sortEmployeesBySalary();
    List<EmployeeDTO> sortEmployeesByName();
}
