package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.mapper.EntityMapper;
import com.LogisticsCompany.model.Employee;
import com.LogisticsCompany.repository.EmployeeRepository;
import com.LogisticsCompany.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EntityMapper entityMapper;

    public EmployeeDTO createOrUpdateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
        if(existingEmployee != null) {
            //The employee already exists
            BeanUtils.copyProperties(employeeDTO, existingEmployee, "id");
            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            return entityMapper.mapToEmployeeDTO(updatedEmployee);
        }
        else {
            //Create a new employee
            Employee newEmployee = entityMapper.mapToEmployee(employeeDTO);
            Employee savedEmployee = employeeRepository.save(newEmployee);
            return entityMapper.mapToEmployeeDTO(savedEmployee);
        }


    }


}
