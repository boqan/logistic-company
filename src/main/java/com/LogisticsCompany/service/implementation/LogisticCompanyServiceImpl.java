package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.dto.LogisticCompanyDTOnoOffice;
import com.LogisticsCompany.dto.OfficeDTOnoCompany;
import com.LogisticsCompany.error.CompanyNoOfficesException;
import com.LogisticsCompany.error.LogisticCompanyNotFoundException;
import com.LogisticsCompany.mapper.EntityMapper;
import com.LogisticsCompany.model.LogisticCompany;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;
import com.LogisticsCompany.repository.LogisticCompanyRepository;
import com.LogisticsCompany.service.LogisticCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogisticCompanyServiceImpl implements LogisticCompanyService {
    @Autowired
    private LogisticCompanyRepository logisticCompanyRepository;
    @Autowired
    private EntityMapper entityMapper;

    @Override
    public LogisticCompanyDTOnoOffice fetchCompanyById(Long companyId) throws LogisticCompanyNotFoundException {
        Optional<LogisticCompany> logisticCompany = logisticCompanyRepository.findById(companyId);
        if(!logisticCompany.isPresent()){
            throw new LogisticCompanyNotFoundException("Logistic Company Not Available");
        }

        LogisticCompanyDTOnoOffice companyDTO = entityMapper.mapToDTOLogisticsCompanyNoCompany(logisticCompany.get());
        return companyDTO;
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
            throw new CompanyNoOfficesException("Company has no offices ");
        }

        Map<Office,List<Order>> currMap=null;

        for(Office office:logisticCompanyDb.getOffices()){
            currMap.put(office,office.getOrders());
        }

        return currMap;
    }

    @Override
    public BigDecimal fetchRevenue(Long companyId) throws LogisticCompanyNotFoundException {
        Optional<LogisticCompany> currLogisticCompany = logisticCompanyRepository.findById(companyId);
        if(!currLogisticCompany.isPresent()){
            throw new LogisticCompanyNotFoundException("Logistic Company Not Available");
        }
        LogisticCompany logisticCompanyDb=currLogisticCompany.get();

        BigDecimal revenue=BigDecimal.ZERO;

        for(Office office:logisticCompanyDb.getOffices()){
            revenue.add(office.getRevenue());
        }

        return revenue;
    }

    @Override
    public List<OfficeDTOnoCompany> fetchOfficesSortedByRevenue(Long companyId) throws LogisticCompanyNotFoundException {
        Optional<LogisticCompany> currLogisticCompany = logisticCompanyRepository.findById(companyId);
        if(!currLogisticCompany.isPresent()){
            throw new LogisticCompanyNotFoundException("Logistic Company Not Available");
        }
        LogisticCompany logisticCompanyDb=currLogisticCompany.get();

        List<OfficeDTOnoCompany> officeDTOnoCompanyList = entityMapper.mapOfficeListDTOnoCompany(logisticCompanyDb.getOffices());
        officeDTOnoCompanyList.sort(Comparator.comparing(OfficeDTOnoCompany::getRevenue));
        return officeDTOnoCompanyList;
    }

    @Override
    public List<OfficeDTOnoCompany> fetchOfficesSortedByNumberOfEmployees(Long companyId) throws LogisticCompanyNotFoundException {
        Optional<LogisticCompany> currLogisticCompany = logisticCompanyRepository.findById(companyId);
        if(!currLogisticCompany.isPresent()){
            throw new LogisticCompanyNotFoundException("Logistic Company Not Available");
        }
        LogisticCompany logisticCompanyDb=currLogisticCompany.get();

        List<OfficeDTOnoCompany> officeDTOnoCompanyList = entityMapper.mapOfficeListDTOnoCompany(logisticCompanyDb.getOffices());

        officeDTOnoCompanyList.sort(Comparator.comparing(officeDTOnoCompany -> officeDTOnoCompany.getEmployees().size()));
        return officeDTOnoCompanyList;
    }

    @Override
    public List<EmployeeDTO> fetchEmployeesSortedBySalary(Long companyId) throws LogisticCompanyNotFoundException {
        Optional<LogisticCompany> currLogisticCompany = logisticCompanyRepository.findById(companyId);
        if(!currLogisticCompany.isPresent()){
            throw new LogisticCompanyNotFoundException("Logistic Company Not Available");
        }
        LogisticCompany logisticCompanyDb = currLogisticCompany.get();

        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for(Office office:logisticCompanyDb.getOffices()){
            employeeDTOList.addAll(entityMapper.mapEmployeeListToDTO(office.getEmployees()));
        }
        return employeeDTOList;
    }

    @Override
    public List<EmployeeDTO> fetchEmployeesByName(Long companyId, String name) throws LogisticCompanyNotFoundException {
        Optional<LogisticCompany> currLogisticCompany = logisticCompanyRepository.findById(companyId);
        if(!currLogisticCompany.isPresent()){
            throw new LogisticCompanyNotFoundException("Logistic Company Not Available");
        }
        LogisticCompany logisticCompanyDb = currLogisticCompany.get();

        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for(Office office:logisticCompanyDb.getOffices()){
            employeeDTOList.addAll(entityMapper.mapEmployeeListToDTO(office.getEmployees()));
        }
        return employeeDTOList.stream()
                .filter(employeeDTOnoOffice -> employeeDTOnoOffice.getName().equals(name))
                .collect(Collectors.toList());
    }


}
