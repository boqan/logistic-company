package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.dto.OfficeDto;
import com.LogisticsCompany.dto.OrderDTOSenderReceiverWithIds;
import com.LogisticsCompany.dto.OrderDto;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.InvalidStatusException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;

import java.util.List;

/**
 * Provides services related to office management, including operations for fetching, saving, updating,
 * and deleting office records. It also includes methods for managing orders and employees associated with
 * an office, such as fetching orders by their delivery status and managing employee details based on salary
 * and name.
 */
public interface OfficeService {

    /**
     * Fetches orders by delivery status for a specified office.
     *
     * @param officeId The ID of the office.
     * @param deliveryStatus The delivery status of orders to fetch.
     * @return A list of OrderDTOSenderReceiverWithIds representing the orders.
     * @throws OfficeNotFoundException if the office is not found.
     * @throws InvalidStatusException if the delivery status is invalid.
     */
    List<OrderDTOSenderReceiverWithIds> fetchOrdersByDeliveryStatus(Long officeId, DeliveryStatus deliveryStatus) throws OfficeNotFoundException, InvalidStatusException;

    /**
     * Fetches details of an office by its ID.
     *
     * @param officeId The ID of the office to fetch.
     * @return An OfficeDto containing the office details.
     * @throws OfficeNotFoundException if the office is not found.
     */
    OfficeDto fetchOfficeById(Long officeId) throws OfficeNotFoundException;

    /**
     * Retrieves an office entity by its ID.
     *
     * @param officeId The ID of the office to fetch.
     * @return The Office entity.
     * @throws OfficeNotFoundException if the office is not found.
     */
    Office fetchOfficeByIdReturnsEntity(Long officeId) throws OfficeNotFoundException;

    /**
     * Saves an office to the repository.
     *
     * @param office The Office entity to be saved.
     * @return An OfficeDto containing the saved office details.
     */
    OfficeDto saveOffice(Office office);

    /**
     * Retrieves a list of all offices.
     *
     * @return A list of OfficeDto representing all offices.
     */
    List<OfficeDto> fetchOfficeList();

    /**
     * Deletes an office by its ID.
     *
     * @param officeId The ID of the office to delete.
     * @throws OfficeNotFoundException if the office is not found.
     */
    void deleteOfficeById(Long officeId) throws OfficeNotFoundException;

    /**
     * Updates an existing office with provided details.
     *
     * @param officeId The ID of the office to update.
     * @param office The new office details.
     * @return An OfficeDto containing the updated office details.
     * @throws OfficeNotFoundException if the office is not found.
     */
    OfficeDto updateOffice(Long officeId, Office office) throws OfficeNotFoundException;

    /**
     * Fetches a list of employees sorted by their salary in ascending order.
     *
     * @param office The office whose employees are to be fetched.
     * @return A list of EmployeeDTO representing the sorted employees.
     */
    List<EmployeeDTO> fetchEmployeesSortedBySalary(Office office);

    /**
     * Updates the orders of an office with a new order.
     *
     * @param newOrder The new order to add.
     * @param fetchedDefaultOffice The office to which the order is added.
     * @return An OfficeDto with updated order details.
     * @throws OfficeNotFoundException if the office is not found.
     */
    OfficeDto updateOfficeOrders(Order newOrder, Office fetchedDefaultOffice) throws OfficeNotFoundException;

    /**
     * Fetches employees with a salary above a specified amount.
     *
     * @param office The office whose employees are to be fetched.
     * @param salary The salary threshold.
     * @return A list of EmployeeDTO for employees with salary above the threshold.
     */
    List<EmployeeDTO> fetchEmployeesAboveSalary(Office office, double salary);

    /**
     * Fetches employees with a salary below a specified amount.
     *
     * @param office The office whose employees are to be fetched.
     * @param salary The salary threshold.
     * @return A list of EmployeeDTO for employees with salary below the threshold.
     */
    List<EmployeeDTO> fetchEmployeesBelowSalary(Office office, double salary);

    /**
     * Fetches employees by their name.
     *
     * @param office The office whose employees are to be fetched.
     * @param name The name of the employees to filter by.
     * @return A list of EmployeeDTO for employees matching the specified name.
     */
    List<EmployeeDTO> fetchEmployeesByName(Office office, String name);

    /**
     * Fetches a list of orders sent by a specific client from a specified office.
     *
     * @param officeId The ID of the office.
     * @param clientId The ID of the client who sent the orders.
     * @return A list of OrderDTOSenderReceiverWithIds representing the orders sent by the client.
     * @throws OfficeNotFoundException if the office is not found.
     */
    List<OrderDTOSenderReceiverWithIds> fetchClientListOfOrdersSender(Long officeId, Long clientId) throws OfficeNotFoundException;

    /**
     * Fetches a list of orders received by a specific client from a specified office.
     *
     * @param officeId The ID of the office.
     * @param clientId The ID of the client who received the orders.
     * @return A list of OrderDTOSenderReceiverWithIds representing the orders received by the client.
     * @throws OfficeNotFoundException if the office is not found.
     */
    List<OrderDTOSenderReceiverWithIds> fetchClientListOfOrdersReceiver(Long officeId, Long clientId) throws OfficeNotFoundException;
}
