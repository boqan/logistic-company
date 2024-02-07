package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.enums.EmployeeType;
import com.LogisticsCompany.error.InvalidDTOException;
import com.LogisticsCompany.model.Office;

import java.util.List;

/**
 * Interface for employee service operations. It provides functionality for managing employees,
 * including saving, updating, and deleting employee records, as well as retrieving employee details
 * by various attributes such as ID, name, and type. It also includes methods for sorting employees
 * by salary and name.
 */
public interface EmployeeService {

    /**
     * Saves a new employee or updates an existing one based on the provided data.
     *
     * @param employeeDTO the {@link EmployeeDTO} object containing the employee's data.
     * @return the {@link EmployeeDTO} representing the saved or updated employee.
     * @throws InvalidDTOException if the provided DTO is invalid.
     */
    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) throws InvalidDTOException;

    /**
     * Updates the information of an existing employee.
     *
     * @param employeeDTO the {@link EmployeeDTO} object containing the updated employee data.
     * @param id the ID of the employee to update.
     * @throws InvalidDTOException if the provided DTO is invalid.
     */
    void updateEmployee(EmployeeDTO employeeDTO, Long id) throws InvalidDTOException;

    /**
     * Deletes an employee by their ID.
     *
     * @param id the ID of the employee to delete.
     */
    void deleteEmployeeById(Long id);

    /**
     * Finds an employee by their ID.
     *
     * @param id the ID of the employee to find.
     * @return the {@link EmployeeDTO} of the found employee.
     */
    EmployeeDTO findEmployeeById(Long id);

    /**
     * Finds an employee by their name.
     *
     * @param name the name of the employee to find.
     * @return the {@link EmployeeDTO} of the found employee.
     */
    EmployeeDTO findEmployeeByName(String name);

    /**
     * Finds employees by their type.
     *
     * @param employeeType the type of the employees to find.
     * @return a list of {@link EmployeeDTO}s of the found employees.
     */
    List<EmployeeDTO> findEmployeesByType(EmployeeType employeeType);

    /**
     * Retrieves all employees.
     *
     * @return a list of {@link EmployeeDTO}s representing all employees.
     */
    List<EmployeeDTO> getAllEmployees();

    /**
     * Sorts employees by their salary in ascending order.
     *
     * @return a list of {@link EmployeeDTO}s sorted by salary.
     */
    List<EmployeeDTO> sortEmployeesBySalary();

    /**
     * Sorts employees by their name in alphabetical order.
     *
     * @return a list of {@link EmployeeDTO}s sorted by name.
     */
    List<EmployeeDTO> sortEmployeesByName();
}