package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.enums.EmployeeType;
import com.LogisticsCompany.error.InvalidDTOException;
import com.LogisticsCompany.model.Office;

import java.util.List;

public interface EmployeeService {
    void saveEmployee(EmployeeDTO employeeDTO) throws InvalidDTOException;
    void updateEmployee(EmployeeDTO employeeDTO, Long id) throws InvalidDTOException;
    void updateEmployeeUsingEmail(EmployeeDTO employeeDTO, String email) throws InvalidDTOException;
    void deleteEmployeeById(Long id);
    void deleteEmployeeByEmail(String email);
    EmployeeDTO findEmployeeById(Long id);
    EmployeeDTO findEmployeeByName(String name);
    List<EmployeeDTO> findEmployeesByType(EmployeeType employeeType);
    EmployeeDTO findEmployeeByEmail(String email);
    List<EmployeeDTO> getAllEmployees();
    List<EmployeeDTO> sortEmployeesBySalary();
    List<EmployeeDTO> sortEmployeesByName();
}