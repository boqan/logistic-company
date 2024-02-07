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

    /**
     * Fetches a logistic company by its ID.
     *
     * @param companyId the ID of the logistic company to fetch.
     * @return the DTO of the fetched logistic company.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
    @Override
    public LogisticCompanyDto fetchCompanyById(Long companyId) throws LogisticCompanyNotFoundException {
        Optional<LogisticCompany> logisticCompany = logisticCompanyRepository.findById(companyId);
        if(!logisticCompany.isPresent()){
            throw new LogisticCompanyNotFoundException("Logistic Company Not Available");
        }

        LogisticCompanyDto companyDTO = entityMapper.mapToDTOLogisticsCompanyNoCompany(logisticCompany.get());
        return companyDTO;
    }


    /**
     * Retrieves a list of all logistic companies.
     *
     * @return a list of DTOs representing all logistic companies.
     */
    @Override
    public List<LogisticCompanyDto> fetchLogisticCompanyList() {
        return entityMapper.mapLogisticCompanyListDTOnoCompany(logisticCompanyRepository.findAll());
    }
    /**
     * Saves a new logistic company or updates an existing one in the database.
     *
     * @param logisticCompany the logistic company to save or update.
     * @return the DTO representation of the saved logistic company.
     */
    @Override
    public LogisticCompanyDto saveLogisticCompany(LogisticCompany logisticCompany) {
        return entityMapper.mapToDTOLogisticsCompanyNoCompany(logisticCompanyRepository.save(logisticCompany));
    }
    /**
     * Deletes a logistic company by its ID.
     *
     * @param companyId the ID of the logistic company to delete.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
    @Override
    public void deleteLogisticCompanyById(Long companyId) throws LogisticCompanyNotFoundException {
        Optional<LogisticCompany> logisticCompany = logisticCompanyRepository.findById(companyId);
        if(!logisticCompany.isPresent()){
            throw new LogisticCompanyNotFoundException("Logistic Company Not Available");
        }
        logisticCompanyRepository.deleteById(companyId);
    }
    /**
     * Updates the details of an existing logistic company.
     *
     * @param companyId        the ID of the logistic company to update.
     * @param logisticCompany the new details for the logistic company.
     * @return the DTO representation of the updated logistic company.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
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
    /**
     * Fetches a map of offices to their orders for a specific logistic company.
     *
     * @param companyId the ID of the logistic company.
     * @return a map where each key is an office and its value is the list of orders for that office.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     * @throws CompanyNoOfficesException if the company has no offices.
     */
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
    /**
     * Calculates the total revenue for a logistic company.
     *
     * @param companyId the ID of the logistic company.
     * @return the total revenue as a BigDecimal.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
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
    /**
     * Fetches a list of offices for a logistic company, sorted by revenue in ascending order.
     *
     * @param companyId the ID of the logistic company.
     * @return a list of office DTOs sorted by revenue.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
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
    /**
     * Fetches a list of offices for a logistic company, sorted by the number of employees in ascending order.
     *
     * @param companyId the ID of the logistic company.
     * @return a list of office DTOs sorted by the number of employees.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
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
    /**
     * Fetches a list of employees for a logistic company, sorted by salary in ascending order.
     *
     * @param companyId the ID of the logistic company.
     * @return a list of employee DTOs sorted by salary.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
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
    /**
     * Fetches employees by name within a logistic company.
     *
     * @param companyId the ID of the logistic company.
     * @param name the name of the employee(s) to fetch.
     * @return a list of employee DTOs that match the given name.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
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
    /**
     * Links an office to a logistic company, adding the office to the company's list of offices.
     *
     * @param companyId the ID of the logistic company to link the office to.
     * @param officeId the ID of the office to link.
     * @return the DTO representation of the logistic company after adding the office.
     * @throws OfficeNotFoundException if the office is not found or already linked to the company.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
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