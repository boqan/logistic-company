package com.LogisticsCompany.controller;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.error.InvalidSalaryException;
import com.LogisticsCompany.model.Employee;
//import com.LogisticsCompany.service.EmployeeService;
import com.LogisticsCompany.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<EmployeeDTO> findEmployeeById(@PathVariable("id") Long id) {
        // It will return the DTO as the body
        return ResponseEntity.ok(employeeService.findEmployeeById(id));
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<EmployeeDTO> findEmployeeByName(@PathVariable("name") String name) {
        // It will return the DTO as the body
        return ResponseEntity.ok(employeeService.findEmployeeByName(name));
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllEmployees() {
        // It will return the DTO as the body
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/sortByName")
    public ResponseEntity sortEmployeesByName() {
        // It will return the DTO as the body
        return ResponseEntity.ok(employeeService.sortEmployeesByName());
    }

    // From biggest to lowest or vice versa? Right not it's from lowest to biggest
    @GetMapping("/sortBySalary")
    public ResponseEntity sortEmployeesBySalary() {
        // It will return the DTO as the body
        return ResponseEntity.ok(employeeService.sortEmployeesBySalary());
    }

    @PostMapping("/save")
    public ResponseEntity saveEmployee(@Valid @RequestBody EmployeeDTO employee) throws InvalidSalaryException {
        employeeService.saveEmployee(employee);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployee(@Valid @RequestBody EmployeeDTO employee, @PathVariable Long id) throws InvalidSalaryException {
        employeeService.updateEmployee(employee, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployeeById(@PathVariable("id") Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
