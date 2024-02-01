package com.LogisticsCompany.controller;


import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.dto.LogisticCompanyDto;
import com.LogisticsCompany.dto.OfficeDto;
import com.LogisticsCompany.error.CompanyNoOfficesException;
import com.LogisticsCompany.error.LogisticCompanyNotFoundException;
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

@RestController
@RequestMapping("/company")
public class LogisticCompanyController {

    @Autowired
    private LogisticCompanyService logisticCompanyService;


    @GetMapping("/order/{id}")
    public ResponseEntity<Map< Office, List<Order>>> fetchOrders(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException, CompanyNoOfficesException {
        Map< Office, List<Order>> orders = logisticCompanyService.fetchOrdersMap(companyId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<List<LogisticCompanyDto>> fetchLogisticCompanyList(){
        List<LogisticCompanyDto> logisticCompanyList = logisticCompanyService.fetchLogisticCompanyList();
        return ResponseEntity.ok(logisticCompanyList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogisticCompanyDto> fetchLogisticCompanyById (@PathVariable("id") Long companyId) throws LogisticCompanyNotFoundException {
        LogisticCompanyDto logisticCompanyDto = logisticCompanyService.fetchCompanyById(companyId);
        return ResponseEntity.ok(logisticCompanyDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartmentById(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException {
        logisticCompanyService.deleteLogisticCompanyById(companyId);
        return ResponseEntity.ok("Company with id " + companyId + " was deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<LogisticCompanyDto> updateCompany(@PathVariable("id")Long companyId ,
                                         @RequestBody LogisticCompany logisticCompany) throws LogisticCompanyNotFoundException {
        LogisticCompanyDto logisticCompanyDto = logisticCompanyService.updateLogisticCompany(companyId,logisticCompany);
        return ResponseEntity.ok(logisticCompanyDto);
    }

    @GetMapping("/{id}/revenue")
    public ResponseEntity<BigDecimal> getRevenue(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException {
        BigDecimal revenue = logisticCompanyService.fetchRevenue(companyId);
        return ResponseEntity.ok(revenue);
    }

    @GetMapping("/{id}/offices_sortedByRevenue")
    public ResponseEntity<List<OfficeDto>> sortOfficesByRevenue(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException {
        List<OfficeDto> offices = logisticCompanyService.fetchOfficesSortedByRevenue(companyId);
        return ResponseEntity.ok(offices);
    }

    @GetMapping("/{id}/offices_sortedByNumberOfEmployees")
    public ResponseEntity<List<OfficeDto>> sortOfficesByNumberOfEmployees(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException {
        List<OfficeDto> offices = logisticCompanyService.fetchOfficesSortedByNumberOfEmployees(companyId);
        return ResponseEntity.ok(offices);
    }

    @GetMapping("/{id}/employees_sortedBySalary")
    public ResponseEntity<List<EmployeeDTO>> sortEmployeesBySalary(@PathVariable("id")Long companyId) throws LogisticCompanyNotFoundException {
        List<EmployeeDTO> employees = logisticCompanyService.fetchEmployeesSortedBySalary(companyId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}/employees_byName/{name}")
    public ResponseEntity<List<EmployeeDTO>> fetchEmployeesByName(Long companyId, String name) throws LogisticCompanyNotFoundException {
        List<EmployeeDTO> employees = logisticCompanyService.fetchEmployeesByName(companyId,name);
        return ResponseEntity.ok(employees);
    }





}
