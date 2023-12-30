package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.error.InvalidSalaryException;
import com.LogisticsCompany.mapper.EntityMapper;
import com.LogisticsCompany.model.Employee;
import com.LogisticsCompany.repository.EmployeeRepository;
import com.LogisticsCompany.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EntityMapper entityMapper;

    @Override
    public EmployeeDTO createOrUpdateEmployee(Long id, EmployeeDTO employeeDTO) throws InvalidSalaryException {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);

        if(existingEmployee != null) {
            //The employee already exists
            BeanUtils.copyProperties(employeeDTO, existingEmployee, "id");
            Employee updatedEmployee = employeeRepository.save(existingEmployee);

            return entityMapper.mapToEmployeeDTO(updatedEmployee);
        }
        else {
            if (employeeDTO.getSalary() <= 0) {
                throw new InvalidSalaryException("Salary cannot be negative");
            }
            //Create a new employee
            Employee newEmployee = entityMapper.mapToEmployee(employeeDTO);
            Employee savedEmployee = employeeRepository.save(newEmployee);

            return entityMapper.mapToEmployeeDTO(savedEmployee);
        }
    }

    @Override
    public void deleteEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with ID " + id + " not found"));

        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeDTO findEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with ID " + id + " not found"));

        EmployeeDTO employeeDTO = entityMapper.mapToEmployeeDTO(employee);

        return employeeDTO;
    }

    @Override
    public EmployeeDTO findEmployeeByName(String name) {
        Employee employee = employeeRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Employee with name " + name + " not found"));

        EmployeeDTO employeeDTO = entityMapper.mapToEmployeeDTO(employee);

        return employeeDTO;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        if (employees.isEmpty()) {
            throw new EntityNotFoundException("No employees found in the database");
        }

        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(employee -> entityMapper.mapToEmployeeDTO(employee))
                .collect(Collectors.toList());

        return employeeDTOs;
    }

    // This may be changed in the future as it works with double not BigDecimal
    @Override
    public List<EmployeeDTO> sortEmployeesBySalary() {
        List<Employee> employees = employeeRepository.findAll();

        if (employees.isEmpty()) {
            throw new EntityNotFoundException("No employees found in the database");
        }

        List<EmployeeDTO> sortedEmployeeDTOs = employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary)) // Sort by salary in ascending order
                .map(employee -> entityMapper.mapToEmployeeDTO(employee))
                .collect(Collectors.toList());

        return sortedEmployeeDTOs;
    }

    @Override
    public List<EmployeeDTO> sortEmployeesByName() {
        List<Employee> employees = employeeRepository.findAll();

        if (employees.isEmpty()) {
            throw new EntityNotFoundException("No employees found in the database");
        }

        List<EmployeeDTO> sortedEmployeeDTOs = employees.stream()
                .sorted(Comparator.comparing(Employee::getName)) // Sort by employee name in ascending order
                .map(employee -> entityMapper.mapToEmployeeDTO(employee))
                .collect(Collectors.toList());

        return sortedEmployeeDTOs;
    }
}
