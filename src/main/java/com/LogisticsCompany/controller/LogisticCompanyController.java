package com.LogisticsCompany.controller;


import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.dto.LogisticCompanyDto;
import com.LogisticsCompany.dto.OfficeDto;
import com.LogisticsCompany.error.CompanyNoOfficesException;
import com.LogisticsCompany.error.LogisticCompanyNotFoundException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.model.LogisticCompany;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;
import com.LogisticsCompany.service.LogisticCompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
/**
 * Controller for managing logistic company operations.
 * Provides endpoints for fetching orders by company, listing logistic companies, fetching specific logistic company details,
 * deleting a logistic company, updating logistic company information, calculating company revenue, sorting offices by revenue or number of employees,
 * sorting employees by salary, fetching employees by name, and linking offices to a company.
 */
@RestController
@RequestMapping("/company")
public class LogisticCompanyController {

    @Autowired
    private LogisticCompanyService logisticCompanyService;

    /**
     * Fetches orders for a specific company identified by its ID.
     *
     * @param companyId the ID of the company
     * @return a ResponseEntity containing a map of offices to their list of orders
     * @throws LogisticCompanyNotFoundException if the company is not found
     * @throws CompanyNoOfficesException if the company has no offices
     */
    @GetMapping("/ordersByCompany/{id}")
    public ResponseEntity<Map< Office, List<Order>>> fetchOrders(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException, CompanyNoOfficesException {
        Map< Office, List<Order>> orders = logisticCompanyService.fetchOrdersMap(companyId);
        return ResponseEntity.ok(orders);
    }


    /**
     * Fetches a list of all logistic companies.
     *
     * @return a ResponseEntity containing a list of LogisticCompanyDto objects
     */
    @GetMapping
    public ResponseEntity<List<LogisticCompanyDto>> fetchLogisticCompanyList(){
        List<LogisticCompanyDto> logisticCompanyList = logisticCompanyService.fetchLogisticCompanyList();
        return ResponseEntity.ok(logisticCompanyList);
    }
    /**
     * Fetches a logistic company by its ID.
     *
     * @param companyId the ID of the company to fetch
     * @return a ResponseEntity containing the LogisticCompanyDto of the requested company
     * @throws LogisticCompanyNotFoundException if the company is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<LogisticCompanyDto> fetchLogisticCompanyById (@PathVariable("id") Long companyId) throws LogisticCompanyNotFoundException {
        LogisticCompanyDto logisticCompanyDto = logisticCompanyService.fetchCompanyById(companyId);
        return ResponseEntity.ok(logisticCompanyDto);
    }
    /**
     * Deletes a logistic company by its ID.
     *
     * @param companyId the ID of the company to delete
     * @return a ResponseEntity containing a confirmation message
     * @throws LogisticCompanyNotFoundException if the company is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartmentById(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException {
        logisticCompanyService.deleteLogisticCompanyById(companyId);
        return ResponseEntity.ok("Company with id " + companyId + " was deleted");
    }
    /**
     * Updates information for an existing logistic company.
     *
     * @param companyId the ID of the company to update
     * @param logisticCompany the new logistic company information to update
     * @return a ResponseEntity containing the updated LogisticCompanyDto
     * @throws LogisticCompanyNotFoundException if the company is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<LogisticCompanyDto> updateCompany(@PathVariable("id")Long companyId ,
                                         @RequestBody LogisticCompany logisticCompany) throws LogisticCompanyNotFoundException {
        LogisticCompanyDto logisticCompanyDto = logisticCompanyService.updateLogisticCompany(companyId,logisticCompany);
        return ResponseEntity.ok(logisticCompanyDto);
    }
    /**
     * Fetches the total revenue for a logistic company by its ID.
     *
     * @param companyId the ID of the company
     * @return a ResponseEntity containing the company's total revenue
     * @throws LogisticCompanyNotFoundException if the company is not found
     */
    @GetMapping("/{id}/revenue")
    public ResponseEntity<BigDecimal> getRevenue(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException {
        BigDecimal revenue = logisticCompanyService.fetchRevenue(companyId);
        return ResponseEntity.ok(revenue);
    }
    /**
     * Sorts and returns a list of offices by their revenue for a specific company.
     *
     * @param companyId the ID of the company
     * @return a ResponseEntity containing a list of OfficeDto sorted by revenue
     * @throws LogisticCompanyNotFoundException if the company is not found
     */
    @GetMapping("/{id}/offices_sortedByRevenue")
    public ResponseEntity<List<OfficeDto>> sortOfficesByRevenue(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException {
        List<OfficeDto> offices = logisticCompanyService.fetchOfficesSortedByRevenue(companyId);
        return ResponseEntity.ok(offices);
    }
    /**
     * Sorts and returns a list of offices by the number of employees for a specific company.
     *
     * @param companyId the ID of the company
     * @return a ResponseEntity containing a list of OfficeDto sorted by the number of employees
     * @throws LogisticCompanyNotFoundException if the company is not found
     */
    @GetMapping("/{id}/offices_sortedByNumberOfEmployees")
    public ResponseEntity<List<OfficeDto>> sortOfficesByNumberOfEmployees(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException {
        List<OfficeDto> offices = logisticCompanyService.fetchOfficesSortedByNumberOfEmployees(companyId);
        return ResponseEntity.ok(offices);
    }

    /**
     * Sorts and returns a list of employees by their salary for a specific company.
     *
     * @param companyId the ID of the company
     * @return a ResponseEntity containing a list of EmployeeDTO sorted by salary
     * @throws LogisticCompanyNotFoundException if the company is not found
     */
    @GetMapping("/{id}/employees_sortedBySalary")
    public ResponseEntity<List<EmployeeDTO>> sortEmployeesBySalary(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException {
        List<EmployeeDTO> employees = logisticCompanyService.fetchEmployeesSortedBySalary(companyId);
        return ResponseEntity.ok(employees);
    }
    /**
     * Fetches a list of employees by name for a specific company.
     *
     * @param companyId the ID of the company
     * @param name the name of the employee(s) to search for
     * @return a ResponseEntity containing a list of EmployeeDTO that match the name
     * @throws LogisticCompanyNotFoundException if the company is not found
     */
    @GetMapping("/{id}/employees_byName/{name}")
    public ResponseEntity<List<EmployeeDTO>> fetchEmployeesByName(Long companyId, String name) throws LogisticCompanyNotFoundException {
        List<EmployeeDTO> employees = logisticCompanyService.fetchEmployeesByName(companyId,name);
        return ResponseEntity.ok(employees);
    }
    /**
     * Links an office to a logistic company by their IDs.
     *
     * @param companyId the ID of the logistic company
     * @param officeId the ID of the office to link
     * @return a ResponseEntity containing the updated LogisticCompanyDto
     * @throws LogisticCompanyNotFoundException if the company is not found
     * @throws OfficeNotFoundException if the office is not found
     */
    @PutMapping("/{id}/linkOffice/{officeId}")
    public ResponseEntity<LogisticCompanyDto> linkOfficeToCompany(@PathVariable("id")Long companyId, @PathVariable("officeId")Long officeId) throws LogisticCompanyNotFoundException, OfficeNotFoundException {
        LogisticCompanyDto logisticCompanyDto = logisticCompanyService.linkOfficeToCompany(companyId,officeId);
        return ResponseEntity.ok(logisticCompanyDto);
    }





}
