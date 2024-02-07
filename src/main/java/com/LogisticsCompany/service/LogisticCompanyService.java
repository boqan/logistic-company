package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.dto.LogisticCompanyDto;
import com.LogisticsCompany.dto.OfficeDto;
import com.LogisticsCompany.error.CompanyNoOfficesException;
import com.LogisticsCompany.error.LogisticCompanyNotFoundException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.model.LogisticCompany;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * Interface for logistic company service operations. It outlines the contract for managing logistic companies,
 * including fetching, saving, updating, and deleting logistic company records. It also provides functionality
 * for managing orders, revenues, offices, and employees associated with logistic companies.
 */
public interface LogisticCompanyService {
    /**
     * Fetches a logistic company by its ID.
     *
     * @param companyId the ID of the logistic company to fetch.
     * @return the {@link LogisticCompanyDto} of the fetched logistic company.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
    public LogisticCompanyDto fetchCompanyById(Long companyId) throws LogisticCompanyNotFoundException;

    /**
     * Retrieves a list of all logistic companies.
     *
     * @return a list of {@link LogisticCompanyDto}s representing all logistic companies.
     */
    public List<LogisticCompanyDto> fetchLogisticCompanyList();

    /**
     * Saves a new logistic company or updates an existing one in the database.
     *
     * @param logisticCompany the {@link LogisticCompany} to save or update.
     * @return the {@link LogisticCompanyDto} representation of the saved or updated logistic company.
     */
    LogisticCompanyDto saveLogisticCompany(LogisticCompany logisticCompany);

    /**
     * Deletes a logistic company by its ID.
     *
     * @param companyId the ID of the logistic company to delete.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
    void deleteLogisticCompanyById(Long companyId) throws LogisticCompanyNotFoundException;

    /**
     * Updates the information for an existing logistic company.
     *
     * @param companyId the ID of the logistic company to update.
     * @param logisticCompany the {@link LogisticCompany} object containing the updated information.
     * @return the {@link LogisticCompanyDto} of the updated logistic company.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
    LogisticCompanyDto updateLogisticCompany(Long companyId, LogisticCompany logisticCompany) throws LogisticCompanyNotFoundException;

    /**
     * Fetches a map of offices to their respective orders for a given logistic company.
     *
     * @param companyId the ID of the logistic company.
     * @return a map where each key is an {@link Office} and its value is the list of {@link Order}s for that office.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     * @throws CompanyNoOfficesException if the company has no offices.
     */
    Map<Office, List<Order>> fetchOrdersMap(Long companyId) throws LogisticCompanyNotFoundException, CompanyNoOfficesException;

    /**
     * Calculates the total revenue for a logistic company.
     *
     * @param companyId the ID of the logistic company.
     * @return the total revenue as a {@link BigDecimal}.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
    BigDecimal fetchRevenue(Long companyId) throws LogisticCompanyNotFoundException;

    /**
     * Fetches a list of offices for a logistic company, sorted by revenue in ascending order.
     *
     * @param companyId the ID of the logistic company.
     * @return a list of {@link OfficeDto}s sorted by revenue.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
    List<OfficeDto> fetchOfficesSortedByRevenue(Long companyId) throws LogisticCompanyNotFoundException;

    /**
     * Fetches a list of offices for a logistic company, sorted by the number of employees in ascending order.
     *
     * @param companyId the ID of the logistic company.
     * @return a list of {@link OfficeDto}s sorted by the number of employees.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
    List<OfficeDto> fetchOfficesSortedByNumberOfEmployees(Long companyId) throws LogisticCompanyNotFoundException;

    /**
     * Fetches a list of employees for a logistic company, sorted by salary in ascending order.
     *
     * @param companyId the ID of the logistic company.
     * @return a list of {@link EmployeeDTO}s sorted by salary.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
    List<EmployeeDTO> fetchEmployeesSortedBySalary(Long companyId) throws LogisticCompanyNotFoundException;

    /**
     * Fetches employees by name within a logistic company.
     *
     * @param companyId the ID of the logistic company.
     * @param name the name of the employee(s) to fetch.
     * @return a list of {@link EmployeeDTO}s that match the given name.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
    List<EmployeeDTO> fetchEmployeesByName(Long companyId, String name) throws LogisticCompanyNotFoundException;

    /**
     * Links an office to a logistic company, associating it with the company.
     *
     * @param companyId the ID of the logistic company to link the office to.
     * @param officeId the ID of the office to link.
     * @return the {@link LogisticCompanyDto} representation of the logistic company after the office has been linked.
     * @throws OfficeNotFoundException if the office is not found.
     * @throws LogisticCompanyNotFoundException if the logistic company is not found.
     */
    LogisticCompanyDto linkOfficeToCompany(Long companyId, Long officeId) throws OfficeNotFoundException, LogisticCompanyNotFoundException;
}
