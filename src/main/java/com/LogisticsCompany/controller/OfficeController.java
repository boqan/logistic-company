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
/**
 * Controller responsible for handling office-related operations.
 * Provides functionalities for managing offices, including creating, fetching, updating, and deleting office information,
 * as well as managing employee and order details related to specific offices.
 */
@RestController
@RequestMapping("/office")
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    /**
     * Saves a new office to the database.
     *
     * @param office The office entity to be saved.
     * @return ResponseEntity containing the saved office DTO and HTTP status OK.
     */
    @PostMapping
    public ResponseEntity<OfficeDto> saveOffice(@Valid @RequestBody Office office){
        OfficeDto officeDto = officeService.saveOffice(office);
        return ResponseEntity.ok(officeDto);
    }
    /**
     * Fetches a list of all offices.
     *
     * @return ResponseEntity containing a list of office DTOs and HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<List<OfficeDto>> fetchOfficeList(){
        List<OfficeDto> officeList = officeService.fetchOfficeList();
        return ResponseEntity.ok(officeList);
    }
    /**
     * Retrieves office details by office ID.
     *
     * @param officeId The ID of the office to retrieve.
     * @return ResponseEntity containing the office DTO and HTTP status OK.
     * @throws OfficeNotFoundException if the office with the specified ID is not found.
     */
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

    /**
     * Deletes an office by its ID.
     *
     * @param officeId The ID of the office to delete.
     * @return ResponseEntity containing a success message and HTTP status OK.
     * @throws OfficeNotFoundException if the office with the specified ID is not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOfficeById(@PathVariable("id")Long officeId) throws  OfficeNotFoundException {
        officeService.deleteOfficeById(officeId);
        return ResponseEntity.ok("Office with id " + officeId + " was deleted");
    }
    /**
     * Updates office details.
     *
     * @param officeId The ID of the office to update.
     * @param office The updated office entity.
     * @return ResponseEntity containing the updated office DTO and HTTP status OK.
     * @throws OfficeNotFoundException if the office with the specified ID is not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OfficeDto> updateOffice(@PathVariable("id")Long officeId ,
                                         @RequestBody Office office) throws OfficeNotFoundException {
        OfficeDto officeDto = officeService.updateOffice(officeId,office);
        return ResponseEntity.ok(officeDto);
    }
    /**
     * Retrieves a list of employees sorted by salary within an office.
     *
     * @param office The office entity.
     * @return ResponseEntity containing a list of employee DTOs sorted by salary and HTTP status OK.
     */
    @GetMapping("/{id}/employees_sortedBySalary")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesSortedBySalary(Office office) {
        List<EmployeeDTO> employees = officeService.fetchEmployeesSortedBySalary(office);
        return ResponseEntity.ok(employees);
    }
    /**
     * Retrieves a list of employees with a salary above a specified amount within an office.
     *
     * @param office The office entity.
     * @param salary The salary threshold.
     * @return ResponseEntity containing a list of employee DTOs with a salary above the specified amount and HTTP status OK.
     */
    @GetMapping("/{id}/employees_aboveSalary/{salary}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesAboveSalary(Office office, @PathVariable("salary") double salary) {
        List<EmployeeDTO> employees = officeService.fetchEmployeesAboveSalary(office, salary);
        return ResponseEntity.ok(employees);
    }
    /**
     * Retrieves a list of employees with a salary below a specified amount within an office.
     *
     * @param office The office entity.
     * @param salary The salary threshold.
     * @return ResponseEntity containing a list of employee DTOs with a salary below the specified amount and HTTP status OK.
     */
    @GetMapping("/{id}/employees_belowSalary/{salary}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesBelowSalary(Office office, @PathVariable("salary") double salary) {
        List<EmployeeDTO> employees = officeService.fetchEmployeesBelowSalary(office, salary);
        return ResponseEntity.ok(employees);
    }
    /**
     * Retrieves a list of employees by name within an office.
     *
     * @param office The office entity.
     * @param name The name of the employees to search for.
     * @return ResponseEntity containing a list of employee DTOs that match the name and HTTP status OK.
     */
    @GetMapping("/{id}/employees_byName/{name}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeByName(Office office, @PathVariable("name") String name) {
        List<EmployeeDTO> employees = officeService.fetchEmployeesByName(office, name);
        return ResponseEntity.ok(employees);
    }
    /**
     * Retrieves a list of orders sent or received by a client within a specific office.
     *
     * @param officeId The ID of the office.
     * @param clientId The ID of the client.
     * @return ResponseEntity containing a list of order DTOs for the specified client and HTTP status OK.
     * @throws OfficeNotFoundException if the office with the specified ID is not found.
     */
    @GetMapping("/{id}/client_orders_receiver/{clientId}")
    public ResponseEntity<List<OrderDTOSenderReceiverWithIds>> getClientListOfOrdersReceiver(@PathVariable("id") Long officeId,
                                                @PathVariable("clientId") Long clientId) throws OfficeNotFoundException {
        List<OrderDTOSenderReceiverWithIds> orders = officeService.fetchClientListOfOrdersReceiver(officeId, clientId);
        return ResponseEntity.ok(orders);
    }
    /**
     * Retrieves a list of orders sent by a client within a specific office.
     *
     * @param officeId The ID of the office.
     * @param clientId The ID of the client.
     * @return ResponseEntity containing a list of order DTOs for the specified client and HTTP status OK.
     * @throws OfficeNotFoundException if the office with the specified ID is not found.
     */
    @GetMapping("/{id}/client_orders_sender/{clientId}")
    public ResponseEntity<List<OrderDTOSenderReceiverWithIds>> getClientListOfOrdersSender(@PathVariable("id") Long officeId,
                                                                                             @PathVariable("clientId") Long clientId) throws OfficeNotFoundException {
        List<OrderDTOSenderReceiverWithIds> orders = officeService.fetchClientListOfOrdersSender(officeId, clientId);
        return ResponseEntity.ok(orders);
    }




}
