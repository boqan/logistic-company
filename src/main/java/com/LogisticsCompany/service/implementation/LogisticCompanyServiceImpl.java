package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.error.CompanyNoOfficesException;
import com.LogisticsCompany.error.LogisticCompanyNotFoundException;
import com.LogisticsCompany.model.LogisticCompany;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;
import com.LogisticsCompany.repository.LogisticCompanyRepository;
import com.LogisticsCompany.service.LogisticCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
@Service
public class LogisticCompanyServiceImpl implements LogisticCompanyService {
    @Autowired
    private LogisticCompanyRepository logisticCompanyRepository;

    @Override
    public LogisticCompany fetchCompanyById(Long companyId) throws LogisticCompanyNotFoundException {
        Optional<LogisticCompany> logisticCompany = logisticCompanyRepository.findById(companyId);

        if(!logisticCompany.isPresent()){
            throw new LogisticCompanyNotFoundException("Logistic Company Not Available");
        }
        return logisticCompany.get();
    }

    @Override
    public List<LogisticCompany> fetchLogisticCompanyList() {
        return logisticCompanyRepository.findAll();
    }

    @Override
    public LogisticCompany saveLogisticCompany(LogisticCompany logisticCompany) {
        return logisticCompanyRepository.save(logisticCompany);
    }

    @Override
    public void deleteLogisticCompanyById(Long companyId) throws LogisticCompanyNotFoundException {
        Optional<LogisticCompany> logisticCompany = logisticCompanyRepository.findById(companyId);
        if(!logisticCompany.isPresent()){
            throw new LogisticCompanyNotFoundException("Logistic Company Not Available");
        }
        logisticCompanyRepository.deleteById(companyId);
    }

    @Override
    public LogisticCompany updateLogisticCompany(Long companyId, LogisticCompany logisticCompany) throws LogisticCompanyNotFoundException {
        Optional<LogisticCompany> currLogisticCompany = logisticCompanyRepository.findById(companyId);
        if(!currLogisticCompany.isPresent()){
            throw new LogisticCompanyNotFoundException("Logistic Company Not Available");
        }
        LogisticCompany logisticCompanyDb=currLogisticCompany.get();

        if(Objects.nonNull(logisticCompany.getName()) && !"".equals(logisticCompany.getName())){
            logisticCompanyDb.setName(logisticCompany.getName());
        }

        if(Objects.nonNull(logisticCompany.getRevenue()) && !"".equals(logisticCompany.getRevenue())){
            logisticCompanyDb.setRevenue((logisticCompany.getRevenue()));
        }

        if(Objects.nonNull(logisticCompany.getCountry()) && !"".equals(logisticCompany.getCountry())){
            logisticCompanyDb.setCountry((logisticCompany.getCountry()));
        }

        return logisticCompanyRepository.save(logisticCompanyDb);
    }

    @Override
    public Map<Office, List<Order>> fetchOrdersMap(Long companyId) throws LogisticCompanyNotFoundException, CompanyNoOfficesException {
        Optional<LogisticCompany> currLogisticCompany = logisticCompanyRepository.findById(companyId);
        if(!currLogisticCompany.isPresent()){
            throw new LogisticCompanyNotFoundException("Logistic Company Not Available");
        }
        LogisticCompany logisticCompanyDb=currLogisticCompany.get();

        if(logisticCompanyDb.getOffices().isEmpty()){
            throw new CompanyNoOfficesException("Company has no officess ");
        }

        Map<Office,List<Order>> currMap = null;

        for(Office office:logisticCompanyDb.getOffices()){
            currMap.put(office,office.getOrders());
        }

        return currMap;
    }
}
