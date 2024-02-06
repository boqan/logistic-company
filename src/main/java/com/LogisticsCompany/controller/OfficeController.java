package com.LogisticsCompany.controller;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.dto.OfficeDto;
import com.LogisticsCompany.dto.OrderDTOSenderReceiverWithIds;
import com.LogisticsCompany.dto.OrderDto;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.InvalidStatusException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.service.OfficeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
@RestController
@RequestMapping("/office")
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    @PostMapping
    public ResponseEntity<OfficeDto> saveOffice(@Valid @RequestBody Office office){
        OfficeDto officeDto = officeService.saveOffice(office);
        return ResponseEntity.ok(officeDto);
    }

    @GetMapping
    public ResponseEntity<List<OfficeDto>> fetchOfficeList(){
        List<OfficeDto> officeList = officeService.fetchOfficeList();
        return ResponseEntity.ok(officeList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeDto> getOfficeById(@PathVariable("id") Long officeId)
            throws OfficeNotFoundException {

        OfficeDto officeDto = officeService.fetchOfficeById(officeId);
        return ResponseEntity.ok(officeDto);
    }

/*
    @GetMapping("/orders/{id}/with_status/{status}")
    public ResponseEntity<List<OrderDto>> getOrdersByDeliveryStatus(@PathVariable("id") Long officeId,
                                                    @PathVariable("status") DeliveryStatus deliveryStatus)
            throws OfficeNotFoundException, InvalidStatusException {

        List<OrderDto> orders = officeService.fetchOrdersByDeliveryStatus(officeId,deliveryStatus);
        return ResponseEntity.ok(orders);
    }



 */
/*
    @GetMapping("/orders/{id}/with_Status/{status}/receiver/{clientId}")
    public ResponseEntity<List<OrderDto>> getOrdersByDeliveryStatusAndReceiverId(@PathVariable("id") Long officeId,
                                                                 @PathVariable("status") DeliveryStatus deliveryStatus ,
                                                                 @PathVariable("clientId") Long clientId)
            throws OfficeNotFoundException, InvalidStatusException {

        List<OrderDto> orders = officeService.fetchOrdersByDeliveryStatusAndReceiverId(officeId,deliveryStatus,clientId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{id}/with_status/{status}/sender/{clientId}")
    public ResponseEntity<List<OrderDto>> getOrdersByDeliveryStatusAndSenderId(@PathVariable("id") Long officeId,
                                                               @PathVariable("status") DeliveryStatus deliveryStatus ,
                                                               @PathVariable("clientId") Long clientId)
            throws OfficeNotFoundException, InvalidStatusException {

        List<OrderDto> orders = officeService.fetchOrdersByDeliveryStatusAndSenderId(officeId,deliveryStatus,clientId);
        return ResponseEntity.ok(orders);
    }

 */


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOfficeById(@PathVariable("id")Long officeId) throws  OfficeNotFoundException {
        officeService.deleteOfficeById(officeId);
        return ResponseEntity.ok("Office with id " + officeId + " was deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficeDto> updateOffice(@PathVariable("id")Long officeId ,
                                         @RequestBody Office office) throws OfficeNotFoundException {
        OfficeDto officeDto = officeService.updateOffice(officeId,office);
        return ResponseEntity.ok(officeDto);
    }

    @GetMapping("/{id}/employees_sortedBySalary")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesSortedBySalary(Office office) {
        List<EmployeeDTO> employees = officeService.fetchEmployeesSortedBySalary(office);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}/employees_aboveSalary/{salary}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesAboveSalary(Office office, @PathVariable("salary") double salary) {
        List<EmployeeDTO> employees = officeService.fetchEmployeesAboveSalary(office, salary);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}/employees_belowSalary/{salary}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesBelowSalary(Office office, @PathVariable("salary") double salary) {
        List<EmployeeDTO> employees = officeService.fetchEmployeesBelowSalary(office, salary);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}/employees_byName/{name}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeByName(Office office, @PathVariable("name") String name) {
        List<EmployeeDTO> employees = officeService.fetchEmployeesByName(office, name);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}/client_orders_receiver/{clientId}")
    public ResponseEntity<List<OrderDTOSenderReceiverWithIds>> getClientListOfOrdersReceiver(@PathVariable("id") Long officeId,
                                                @PathVariable("clientId") Long clientId) throws OfficeNotFoundException {
        List<OrderDTOSenderReceiverWithIds> orders = officeService.fetchClientListOfOrdersReceiver(officeId, clientId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}/client_orders_sender/{clientId}")
    public ResponseEntity<List<OrderDTOSenderReceiverWithIds>> getClientListOfOrdersSender(@PathVariable("id") Long officeId,
                                                                                             @PathVariable("clientId") Long clientId) throws OfficeNotFoundException {
        List<OrderDTOSenderReceiverWithIds> orders = officeService.fetchClientListOfOrdersSender(officeId, clientId);
        return ResponseEntity.ok(orders);
    }




}
