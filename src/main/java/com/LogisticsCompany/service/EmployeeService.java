package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.error.InvalidDTOException;

import java.util.List;

public interface EmployeeService {
    void saveEmployee(EmployeeDTO employeeDTO) throws InvalidDTOException;
    void updateEmployee(EmployeeDTO employeeDTO, Long id) throws InvalidDTOException;
    void deleteEmployeeById(Long id);
    EmployeeDTO findEmployeeById(Long id);
    EmployeeDTO findEmployeeByName(String name);
    List<EmployeeDTO> getAllEmployees();
    List<EmployeeDTO> sortEmployeesBySalary();
    List<EmployeeDTO> sortEmployeesByName();

}
