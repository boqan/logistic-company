package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.LogisticCompanyDTOnoCompany;
import com.LogisticsCompany.error.CompanyNoOfficesException;
import com.LogisticsCompany.error.LogisticCompanyNotFoundException;
import com.LogisticsCompany.model.LogisticCompany;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;

import java.util.List;
import java.util.Map;


public interface LogisticCompanyService {
    public LogisticCompanyDTOnoCompany fetchCompanyById(Long companyId) throws LogisticCompanyNotFoundException;

    public List<LogisticCompany> fetchLogisticCompanyList();

    LogisticCompany saveLogisticCompany(LogisticCompany logisticCompany);

    void deleteLogisticCompanyById(Long companyId) throws LogisticCompanyNotFoundException;

    LogisticCompany updateLogisticCompany(Long companyId, LogisticCompany logisticCompany) throws LogisticCompanyNotFoundException;

    Map<Office, List<Order>> fetchOrdersMap(Long companyId) throws LogisticCompanyNotFoundException, CompanyNoOfficesException;
}
