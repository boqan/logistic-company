package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.enums.EmployeeType;
import com.LogisticsCompany.error.InvalidDTOException;
import com.LogisticsCompany.mapper.EmployeeMapper;
import com.LogisticsCompany.model.Employee;
import com.LogisticsCompany.repository.EmployeeRepository;
import com.LogisticsCompany.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeMapper entityMapper;

    @Override
    public void saveEmployee(EmployeeDTO employeeDTO) throws InvalidDTOException {
        validateDTO(employeeDTO);
        Employee newEmployee = entityMapper.DTOToEmployee(employeeDTO);
        employeeRepository.save(newEmployee);
    }

    @Override
    public void updateEmployee(EmployeeDTO employeeDTO, Long id) throws InvalidDTOException {
        validateDTO(employeeDTO);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with ID " + id + " not found"));

        BeanUtils.copyProperties(employeeDTO, employee);
        employeeRepository.save(employee);
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

        return entityMapper.mapToEmployeeDTO(employee);
    }

    @Override
    public EmployeeDTO findEmployeeByName(String name) {
        Employee employee = employeeRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Employee with name " + name + " not found"));

        return entityMapper.mapToEmployeeDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        if (employees.isEmpty()) {
            throw new EntityNotFoundException("No employees found in the database");
        }

        return employees.stream()
                .map(employee -> entityMapper.mapToEmployeeDTO(employee))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> findEmployeesByType(EmployeeType employeeType) {
        List<Employee> employees = employeeRepository.findAllByEmployeeType(employeeType);

        if (employees.isEmpty()) {
            throw new EntityNotFoundException("No employees found with the type " + employeeType);
        }

        return employees.stream()
                .map(entityMapper::mapToEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> sortEmployeesBySalary() {
        List<Employee> employees = employeeRepository.findAll();

        if (employees.isEmpty()) {
            throw new EntityNotFoundException("No employees found in the database");
        }

        return employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary)) // Sort by salary in ascending order
                .map(employee -> entityMapper.mapToEmployeeDTO(employee))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> sortEmployeesByName() {
        List<Employee> employees = employeeRepository.findAll();

        if (employees.isEmpty()) {
            throw new EntityNotFoundException("No employees found in the database");
        }

        return employees.stream()
                .sorted(Comparator.comparing(Employee::getName)) // Sort by employee name in ascending order
                .map(employee -> entityMapper.mapToEmployeeDTO(employee))
                .collect(Collectors.toList());
    }

    private void validateDTO(EmployeeDTO employeeDTO) throws InvalidDTOException {
        if (employeeDTO.getSalary().compareTo(BigDecimal.ZERO) <= 0 || employeeDTO.getName() == null) {
            throw new InvalidDTOException("DTO contains invalid data");
        }
    }
}
