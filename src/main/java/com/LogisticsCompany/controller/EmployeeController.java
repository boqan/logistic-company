package com.LogisticsCompany.controller;

import com.LogisticsCompany.dto.AuthenticationResponce;
import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.dto.RegisterCompanyRequest;
import com.LogisticsCompany.dto.RegisterEmployeeRequest;
import com.LogisticsCompany.enums.EmployeeType;
import com.LogisticsCompany.error.EntityAlreadyExistsInDbException;
import com.LogisticsCompany.error.InvalidDTOException;
import com.LogisticsCompany.service.CredentialsService;
import com.LogisticsCompany.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller for handling requests related to employee operations.
 * Supports finding, saving, updating, and deleting employees, as well as sorting employees by name or salary,
 * and managing employee credentials.
 */
@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CredentialsService credentialsService;
    /**
     * Finds an employee by their ID.
     *
     * @param id The ID of the employee to find.
     * @return ResponseEntity containing the employee data and HTTP status OK.
     */
    @GetMapping("/findById/{id}")
    public ResponseEntity<EmployeeDTO> findEmployeeById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.findEmployeeById(id));
    }
    /**
     * Finds an employee by their name.
     *
     * @param name The name of the employee to find.
     * @return ResponseEntity containing the employee data and HTTP status OK.
     */
    @GetMapping("/findByName/{name}")
    public ResponseEntity<EmployeeDTO> findEmployeeByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(employeeService.findEmployeeByName(name));
    }
    /**
     * Finds employees by their type.
     *
     * @param type The type of employees to find.
     * @return ResponseEntity containing a list of employees of the specified type and HTTP status OK.
     */
    @GetMapping("/findByType/{type}")
    public ResponseEntity<List<EmployeeDTO>> findEmployeesByType(@PathVariable("type") EmployeeType type) {
        return ResponseEntity.ok(employeeService.findEmployeesByType(type));
    }
    /**
     * Retrieves all employees.
     *
     * @return ResponseEntity containing a list of all employees and HTTP status OK.
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    /**
     * Sorts employees by their name.
     *
     * @return ResponseEntity containing a list of employees sorted by name and HTTP status OK.
     */
    @GetMapping("/sortByName")
    public ResponseEntity<List<EmployeeDTO>> sortEmployeesByName() {
        return ResponseEntity.ok(employeeService.sortEmployeesByName());
    }
    /**
     * Sorts employees by their salary.
     *
     * @return ResponseEntity containing a list of employees sorted by salary and HTTP status OK.
     */
    @GetMapping("/sortBySalary")
    public ResponseEntity<List<EmployeeDTO>> sortEmployeesBySalary() {
        return ResponseEntity.ok(employeeService.sortEmployeesBySalary());
    }

    /**
     * Saves an employee to the database.
     *
     * @param employee The employee DTO containing the data to save.
     * @return ResponseEntity with HTTP status CREATED upon successful save.
     * @throws InvalidDTOException If the employee DTO is invalid.
     */
    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveEmployee(@Valid @RequestBody EmployeeDTO employee) throws InvalidDTOException {
        employeeService.saveEmployee(employee);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    /**
     * Updates an existing employee's information.
     *
     * @param employee The employee DTO containing the updated data.
     * @param id The ID of the employee to update.
     * @return ResponseEntity with HTTP status OK upon successful update.
     * @throws InvalidDTOException If the employee DTO is invalid.
     */
    // It could have the functionality of receiveSalary - it will be a PUT request
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateEmployee(@Valid @RequestBody EmployeeDTO employee, @PathVariable Long id) throws InvalidDTOException {
        employeeService.updateEmployee(employee, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param id The ID of the employee to delete.
     * @return ResponseEntity with HTTP status OK upon successful deletion.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteEmployeeById(@PathVariable("id") Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    /**
     * Registers credentials for an employee, typically used for authentication purposes.
     *
     * @param registerEmployeeRequest The request containing the employee's registration data.
     * @return ResponseEntity containing the authentication response and HTTP status OK.
     * @throws EntityAlreadyExistsInDbException If the employee already exists in the database.
     * @throws InvalidDTOException If the registration request is invalid.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponce> saveEmployeeCredentials(@RequestBody RegisterEmployeeRequest registerEmployeeRequest) throws EntityAlreadyExistsInDbException, InvalidDTOException {
        return ResponseEntity.ok(credentialsService.registerEmployee(registerEmployeeRequest));
    }
}
