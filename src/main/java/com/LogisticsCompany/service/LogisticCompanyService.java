package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.dto.LogisticCompanyDto;
import com.LogisticsCompany.dto.OfficeDto;
import com.LogisticsCompany.error.CompanyNoOfficesException;
import com.LogisticsCompany.error.LogisticCompanyNotFoundException;
import com.LogisticsCompany.model.LogisticCompany;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface LogisticCompanyService {
    public LogisticCompanyDto fetchCompanyById(Long companyId) throws LogisticCompanyNotFoundException;

    public List<LogisticCompany> fetchLogisticCompanyList();

    LogisticCompany saveLogisticCompany(LogisticCompany logisticCompany);

    void deleteLogisticCompanyById(Long companyId) throws LogisticCompanyNotFoundException;

    LogisticCompany updateLogisticCompany(Long companyId, LogisticCompany logisticCompany) throws LogisticCompanyNotFoundException;

    Map<Office, List<Order>> fetchOrdersMap(Long companyId) throws LogisticCompanyNotFoundException, CompanyNoOfficesException;

    BigDecimal fetchRevenue(Long companyId) throws LogisticCompanyNotFoundException;

    List<OfficeDto> fetchOfficesSortedByRevenue(Long companyId) throws LogisticCompanyNotFoundException;

    List<OfficeDto> fetchOfficesSortedByNumberOfEmployees(Long companyId) throws LogisticCompanyNotFoundException;

    List<EmployeeDTO> fetchEmployeesSortedBySalary(Long companyId) throws LogisticCompanyNotFoundException;

    List<EmployeeDTO> fetchEmployeesByName(Long companyId, String name) throws LogisticCompanyNotFoundException;
}
