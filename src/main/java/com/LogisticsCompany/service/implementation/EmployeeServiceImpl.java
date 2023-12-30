package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.mapper.EntityMapper;
import com.LogisticsCompany.repository.EmployeeRepository;
import com.LogisticsCompany.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }



}
