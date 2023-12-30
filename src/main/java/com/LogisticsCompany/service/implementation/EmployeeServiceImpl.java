package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.dto.EmployeeDTOnoOffice;
import com.LogisticsCompany.mapper.EntityMapper;
import com.LogisticsCompany.model.Employee;
import com.LogisticsCompany.repository.EmployeeRepository;
import com.LogisticsCompany.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EntityMapper entityMapper;


    public EmployeeDTOnoOffice createOrUpdateEmployee(Long id, EmployeeDTOnoOffice employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
        if(existingEmployee != null) {
            //The employee already exists

        }
        else {
            //Create a new employee
            //Employee newEmployee = entityMapper.mapToEmployeeDTOnoOffice(employeeDTO);

        }
    }


}
