package com.LogisticsCompany.controller;


import com.LogisticsCompany.dto.EmployeeDTOnoOffice;
import com.LogisticsCompany.dto.LogisticCompanyDTOnoOffice;
import com.LogisticsCompany.dto.OfficeDTOnoCompany;
import com.LogisticsCompany.error.CompanyNoOfficesException;
import com.LogisticsCompany.error.LogisticCompanyNotFoundException;
import com.LogisticsCompany.model.LogisticCompany;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;
import com.LogisticsCompany.service.LogisticCompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
public class LogisticCompanyController {

    @Autowired
    private LogisticCompanyService logisticCompanyService;

    @PostMapping("/companies")
    public LogisticCompany saveLogisticCompany(@Valid @RequestBody LogisticCompany logisticCompany){
        return logisticCompanyService.saveLogisticCompany(logisticCompany);
    }

    @GetMapping("/orders/{id}")
    public Map< Office, List<Order>> fetchOrders(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException, CompanyNoOfficesException {

        return logisticCompanyService.fetchOrdersMap(companyId);
    }

    @GetMapping("/companies")
    public List<LogisticCompany> fetchLogisticCompanyList(){
        return logisticCompanyService.fetchLogisticCompanyList();

    }

    @GetMapping("/company/{id}")
    public LogisticCompanyDTOnoOffice fetchLogisticCompanyById (@PathVariable("id") Long companyId) throws LogisticCompanyNotFoundException {
        return logisticCompanyService.fetchCompanyById(companyId);
    }

    @DeleteMapping("/company/{id}")
    public String deleteDepartmentById(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException {
        logisticCompanyService.deleteLogisticCompanyById(companyId);
        return "Logistic Company deleted successfully!!";
    }

    @PutMapping("/company/{id}")
    public LogisticCompany updateCompany(@PathVariable("id")Long companyId ,
                                         @RequestBody LogisticCompany logisticCompany) throws LogisticCompanyNotFoundException {
        return logisticCompanyService.updateLogisticCompany(companyId,logisticCompany);
    }

    @GetMapping("/company/{id}/revenue")
    public BigDecimal getRevenue(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException {
        return logisticCompanyService.fetchRevenue(companyId);
    }

    @GetMapping("/company/{id}/offices_sortedByRevenue")
    public List<OfficeDTOnoCompany> sortOfficesByRevenue(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException {
        return logisticCompanyService.fetchOfficesSortedByRevenue(companyId);
    }

    @GetMapping("/company/{id}/offices_sortedByNumberOfEmployees")
    public List<OfficeDTOnoCompany> sortOfficesByNumberOfEmployees(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException {
        return logisticCompanyService.fetchOfficesSortedByNumberOfEmployees(companyId);
    }

    @GetMapping("/company/{id}/employees_sortedBySalary")
    public List<EmployeeDTOnoOffice> sortEmployeesBySalary(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException {
        return logisticCompanyService.fetchEmployeesSortedBySalary(companyId);
    }

    @GetMapping("/company/{id}/employees_byName/{name}")
    public List<EmployeeDTOnoOffice> fetchEmployeesByName(Long companyId, String name) throws LogisticCompanyNotFoundException {
        return logisticCompanyService.fetchEmployeesByName(companyId, name);
    }





}
