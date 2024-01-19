package com.LogisticsCompany.controller;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.error.InvalidDTOException;
//import com.LogisticsCompany.service.EmployeeService;
import com.LogisticsCompany.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//We should use Controller instead of RestController for frontend purposes
@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // The DTO should not have variable id
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
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        // It will return the DTO as the body
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/sortByName")
    public ResponseEntity<List<EmployeeDTO>> sortEmployeesByName() {
        // It will return the DTO as the body
        return ResponseEntity.ok(employeeService.sortEmployeesByName());
    }

    // From biggest to lowest or vice versa? Right not it's from lowest to biggest
    @GetMapping("/sortBySalary")
    public ResponseEntity<List<EmployeeDTO>> sortEmployeesBySalary() {
        // It will return the DTO as the body
        return ResponseEntity.ok(employeeService.sortEmployeesBySalary());
    }

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveEmployee(@Valid @RequestBody EmployeeDTO employee) throws InvalidDTOException {
        employeeService.saveEmployee(employee);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    // It can have the functionality of receiveSalary - it will be a PUT request
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateEmployee(@Valid @RequestBody EmployeeDTO employee, @PathVariable Long id) throws InvalidDTOException {
        employeeService.updateEmployee(employee, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteEmployeeById(@PathVariable("id") Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InvalidDTOException.class)
    public ResponseEntity<String> handleInvalidDTOException(InvalidDTOException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
