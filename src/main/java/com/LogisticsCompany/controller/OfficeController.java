package com.LogisticsCompany.controller;

import com.LogisticsCompany.dto.EmployeeDto;
import com.LogisticsCompany.dto.OfficeDto;
import com.LogisticsCompany.dto.OrderDto;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.InvalidStatusException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.service.OfficeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    @PostMapping("/offices")
    public Office saveOffice(@Valid @RequestBody Office office){
        return officeService.saveOffice(office);
    }

    @GetMapping("/offices")
    public List<Office> fetchOfficeList(){
        return officeService.fetchOfficeList();

    }

    @GetMapping("/office/{id}")
    public OfficeDto getOfficeById(@PathVariable("id") Long officeId)
            throws OfficeNotFoundException {

        return officeService.fetchOfficeById(officeId);
    }


    @GetMapping("/office_orders/{id}/with_status/{status}")
    public List<OrderDto> getOrdersByDeliveryStatus(@PathVariable("id") Long officeId,
                                                    @PathVariable("status") DeliveryStatus deliveryStatus)
            throws OfficeNotFoundException, InvalidStatusException {

        return officeService.fetchOrdersByDeliveryStatus(officeId,deliveryStatus);
    }

    @GetMapping("/office_orders/{id}/with_Status/{status}/receiver/{clientId}")
    public List<OrderDto> getOrdersByDeliveryStatusAndReceiverId(@PathVariable("id") Long officeId,
                                                                 @PathVariable("status") DeliveryStatus deliveryStatus ,
                                                                 @PathVariable("clientId") Long clientId)
            throws OfficeNotFoundException, InvalidStatusException {

        return officeService.fetchOrdersByDeliveryStatusAndReceiverId(officeId,deliveryStatus,clientId);
    }

    @GetMapping("/office_orders/{id}/with_status/{status}/sender/{clientId}")
    public List<OrderDto> getOrdersByDeliveryStatusAndSenderId(@PathVariable("id") Long officeId,
                                                               @PathVariable("status") DeliveryStatus deliveryStatus ,
                                                               @PathVariable("clientId") Long clientId)
            throws OfficeNotFoundException, InvalidStatusException {

        return officeService.fetchOrdersByDeliveryStatusAndSenderId(officeId,deliveryStatus,clientId);
    }


    @DeleteMapping("/office/{id}")
    public String deleteOfficeById(@PathVariable("id")Long officeId) throws  OfficeNotFoundException {
        officeService.deleteOfficeById(officeId);
        return "Logistic Company deleted successfully!!";
    }

    @PutMapping("/office/{id}")
    public Office updateOffice(@PathVariable("id")Long officeId ,
                                         @RequestBody Office office) throws OfficeNotFoundException {
        return officeService.updateOffice(officeId,office);
    }

    @GetMapping("/office/{id}/employees_sortedBySalary")
    public List<EmployeeDto> getEmployeesSortedBySalary(Office office) {
        return officeService.fetchEmployeesSortedBySalary(office);
    }

    @GetMapping("/office/{id}/employees_aboveSalary/{salary}")
    public List<EmployeeDto> getEmployeesAboveSalary(Office office, @PathVariable("salary") double salary) {
        return officeService.fetchEmployeesAboveSalary(office, salary);
    }

    @GetMapping("/office/{id}/employees_belowSalary/{salary}")
    public List<EmployeeDto> getEmployeesBelowSalary(Office office, @PathVariable("salary") double salary) {
        return officeService.fetchEmployeesBelowSalary(office, salary);
    }

    @GetMapping("/office/{id}/employees_byName/{name}")
    public List<EmployeeDto> getEmployeeByName(Office office, @PathVariable("name") String name) {
        return officeService.fetchEmployeesByName(office, name);
    }

    @GetMapping("/office/{id}/client_orders/{clientId}")
    public List<OrderDto> getClientListOfOrders(@PathVariable("id") Long officeId,
                                                @PathVariable("clientId") Long clientId) throws OfficeNotFoundException {
        return officeService.fetchClientListOfOrders(officeId, clientId);
    }



}
