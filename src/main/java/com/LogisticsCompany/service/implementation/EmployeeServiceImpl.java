package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.mapper.EntityMapper;
import com.LogisticsCompany.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl{
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EntityMapper entityMapper;



}
