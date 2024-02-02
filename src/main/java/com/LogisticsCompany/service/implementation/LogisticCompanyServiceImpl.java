package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.dto.LogisticCompanyDto;
import com.LogisticsCompany.dto.OfficeDto;
import com.LogisticsCompany.error.CompanyNoOfficesException;
import com.LogisticsCompany.error.LogisticCompanyNotFoundException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.mapper.EntityMapper;
import com.LogisticsCompany.model.LogisticCompany;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;
import com.LogisticsCompany.repository.LogisticCompanyRepository;
import com.LogisticsCompany.repository.OfficeRepository;
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

    @Autowired
    private OfficeRepository officeRepository;

    @Override
    public LogisticCompanyDto fetchCompanyById(Long companyId) throws LogisticCompanyNotFoundException {
        Optional<LogisticCompany> logisticCompany = logisticCompanyRepository.findById(companyId);
        if(!logisticCompany.isPresent()){
            throw new LogisticCompanyNotFoundException("Logistic Company Not Available");
        }

        LogisticCompanyDto companyDTO = entityMapper.mapToDTOLogisticsCompanyNoCompany(logisticCompany.get());
        return companyDTO;
    }

    @Override
    public List<LogisticCompanyDto> fetchLogisticCompanyList() {
        return entityMapper.mapLogisticCompanyListDTOnoCompany(logisticCompanyRepository.findAll());
    }

    @Override
    public LogisticCompanyDto saveLogisticCompany(LogisticCompany logisticCompany) {
        return entityMapper.mapToDTOLogisticsCompanyNoCompany(logisticCompanyRepository.save(logisticCompany));
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
    public LogisticCompanyDto updateLogisticCompany(Long companyId, LogisticCompany logisticCompany) throws LogisticCompanyNotFoundException {
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

        return entityMapper.mapToDTOLogisticsCompanyNoCompany(logisticCompanyRepository.save(logisticCompanyDb));
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
    public List<OfficeDto> fetchOfficesSortedByRevenue(Long companyId) throws LogisticCompanyNotFoundException {
        Optional<LogisticCompany> currLogisticCompany = logisticCompanyRepository.findById(companyId);
        if(!currLogisticCompany.isPresent()){
            throw new LogisticCompanyNotFoundException("Logistic Company Not Available");
        }
        LogisticCompany logisticCompanyDb=currLogisticCompany.get();

        List<OfficeDto> officeDtoList = entityMapper.mapOfficeListDTOnoCompany(logisticCompanyDb.getOffices());
        officeDtoList.sort(Comparator.comparing(OfficeDto::getRevenue));
        return officeDtoList;
    }

    @Override
    public List<OfficeDto> fetchOfficesSortedByNumberOfEmployees(Long companyId) throws LogisticCompanyNotFoundException {
        Optional<LogisticCompany> currLogisticCompany = logisticCompanyRepository.findById(companyId);
        if(!currLogisticCompany.isPresent()){
            throw new LogisticCompanyNotFoundException("Logistic Company Not Available");
        }
        LogisticCompany logisticCompanyDb=currLogisticCompany.get();

        List<OfficeDto> officeDtoList = entityMapper.mapOfficeListDTOnoCompany(logisticCompanyDb.getOffices());

        officeDtoList.sort(Comparator.comparing(officeDTOnoCompany -> officeDTOnoCompany.getEmployees().size()));
        return officeDtoList;
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

    @Override
    public LogisticCompanyDto linkOfficeToCompany(Long companyId, Long officeId) throws OfficeNotFoundException, LogisticCompanyNotFoundException {
        Optional<LogisticCompany> currLogisticCompany = logisticCompanyRepository.findById(companyId);
        if(!currLogisticCompany.isPresent()){
            throw new LogisticCompanyNotFoundException("Logistic Company Not Available");
        }
        LogisticCompany logisticCompanyDb = currLogisticCompany.get();

        Optional<Office> currOffice = officeRepository.findById(officeId);
        if(!currOffice.isPresent()){
            throw new OfficeNotFoundException("Office Not Available");
        }
        Office officeDb = currOffice.get();
        // Check if the office is in the company already

        if(logisticCompanyDb.getOffices().contains(officeDb)){
            throw new OfficeNotFoundException("Office already linked to the company");
        }

        logisticCompanyDb.getOffices().add(officeDb);
        officeDb.setLogisticCompany(logisticCompanyDb);


        return entityMapper.mapToDTOLogisticsCompanyNoCompany(logisticCompanyRepository.save(logisticCompanyDb));
    }


}