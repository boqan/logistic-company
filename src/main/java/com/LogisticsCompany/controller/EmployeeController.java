package com.LogisticsCompany.controller;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.enums.EmployeeType;
import com.LogisticsCompany.error.InvalidDTOException;
import com.LogisticsCompany.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/findById/{id}")
    public ResponseEntity<EmployeeDTO> findEmployeeById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.findEmployeeById(id));
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<EmployeeDTO> findEmployeeByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(employeeService.findEmployeeByName(name));
    }

    @GetMapping("/findByType/{type}")
    public ResponseEntity<List<EmployeeDTO>> findEmployeesByType(@PathVariable("type") EmployeeType type) {
        return ResponseEntity.ok(employeeService.findEmployeesByType(type));
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<EmployeeDTO> findEmployeeByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(employeeService.findEmployeeByEmail(email));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/sortByName")
    public ResponseEntity<List<EmployeeDTO>> sortEmployeesByName() {
        return ResponseEntity.ok(employeeService.sortEmployeesByName());
    }

    @GetMapping("/sortBySalary")
    public ResponseEntity<List<EmployeeDTO>> sortEmployeesBySalary() {
        return ResponseEntity.ok(employeeService.sortEmployeesBySalary());
    }

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveEmployee(@Valid @RequestBody EmployeeDTO employee) throws InvalidDTOException {
        employeeService.saveEmployee(employee);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    // It could have the functionality of receiveSalary - it will be a PUT request
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateEmployee(@Valid @RequestBody EmployeeDTO employee, @PathVariable Long id) throws InvalidDTOException {
        employeeService.updateEmployee(employee, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/updateByEmail/{email}")
    public ResponseEntity<HttpStatus> updateEmployeeUsingEmail(@Valid @RequestBody EmployeeDTO employee, @PathVariable String email) throws InvalidDTOException {
        employeeService.updateEmployeeUsingEmail(employee, email);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteEmployeeById(@PathVariable("id") Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/deleteByEmail/{email}")
    public ResponseEntity<HttpStatus> deleteEmployeeByEmail(@PathVariable("email") String email) {
        String cleanedEmail = email.trim();
        employeeService.deleteEmployeeByEmail(cleanedEmail);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
