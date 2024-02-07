package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.dto.OfficeDto;
import com.LogisticsCompany.dto.OrderDTOSenderReceiverWithIds;
import com.LogisticsCompany.dto.OrderDto;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.InvalidStatusException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.mapper.EntityMapper;
import com.LogisticsCompany.model.Employee;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;
import com.LogisticsCompany.repository.OfficeRepository;
import com.LogisticsCompany.service.OfficeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.stream.Collectors;
/**
 * Implements the {@link OfficeService} interface to provide a variety of services related to {@link Office} management.
 * This service class handles operations such as fetching office details by ID, managing office orders based on delivery status,
 * saving and updating office information, managing employees within an office, and handling client order lists.
 * Uses {@link OfficeRepository} for data access and {@link EntityMapper} for converting between entity and DTO objects.
 */
@Service
public class OfficeServiceImpl implements OfficeService {

    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private EntityMapper entityMapper;
    /**
     * Fetches an office by its ID and converts it to an {@link OfficeDto} without the associated company information.
     *
     * @param officeId The ID of the office to fetch.
     * @return The {@link OfficeDto} representation of the office.
     * @throws OfficeNotFoundException if the office with the specified ID is not found.
     */
    @Override   // no need to use custom OfficeNotFoundException we have a default one for all entities
    public OfficeDto fetchOfficeById(Long officeId) throws OfficeNotFoundException {
        Office office = officeRepository.findById(officeId).orElseThrow(() -> new EntityNotFoundException());
        OfficeDto officeDto = entityMapper.mapToOfficeDTOnoCompany(office);
        return officeDto;
    }
    // we use this fetch default office method to get the first office in the database
    // since we need it to create an order and then update the office's orders
    // we would have some logic to check which office is the closest or most optimal,
    // but since we don't have that, we just get the first one as a placeholder way of doing it
    /**
     * Retrieves an office entity by its ID.
     *
     * @param officeId The ID of the office to fetch.
     * @return The {@link Office} entity.
     * @throws OfficeNotFoundException if no office is found with the given ID.
     */
    @Override
    public Office fetchOfficeByIdReturnsEntity(Long officeId) throws OfficeNotFoundException {
        Optional<Office> office = officeRepository.findById(officeId);

        if (office.isEmpty()) {
            throw new OfficeNotFoundException("No offices available");
        }

        return office.get();
    }
    /**
     * Fetches orders by delivery status for a specific office, returning a list of orders with sender and receiver IDs.
     *
     * @param officeId The ID of the office to fetch orders from.
     * @param deliveryStatus The delivery status to filter orders by.
     * @return A list of {@link OrderDTOSenderReceiverWithIds} matching the specified delivery status.
     * @throws OfficeNotFoundException if the specified office is not found.
     * @throws InvalidStatusException if the specified delivery status is invalid.
     */
    @Override
    public List<OrderDTOSenderReceiverWithIds> fetchOrdersByDeliveryStatus(Long officeId, DeliveryStatus deliveryStatus) throws OfficeNotFoundException, InvalidStatusException {
        Optional<Office> office = officeRepository.findById(officeId);
        if(!office.isPresent()){
            throw new OfficeNotFoundException("Office not available");
        }
        if(!DeliveryStatus.isValidStatus(deliveryStatus.name())){
            throw new InvalidStatusException("Invalid delivery status");
        }
        OfficeDto officeDto = entityMapper.mapToOfficeDTOnoCompany(office.get());
        return officeDto.getOrders().stream().
                filter(orderDTOnoOffice -> orderDTOnoOffice
                        .getStatus()
                        .equals(deliveryStatus))
                        .collect(Collectors.toList());
    }

/*
    @Override
    public List<OrderDTOSenderReceiverWithIds> fetchOrdersByDeliveryStatusAndReceiverId(Long officeId, DeliveryStatus deliveryStatus, Long clientId) throws OfficeNotFoundException, InvalidStatusException {
        Optional<Office> office = officeRepository.findById(officeId);
        if(!office.isPresent()){
            throw new OfficeNotFoundException("Office not available");
        }
        if(!DeliveryStatus.isValidStatus(deliveryStatus.name())){
            throw new InvalidStatusException("Invalid delivery status");
        }
        OfficeDto officeDto = entityMapper.mapToOfficeDTOnoCompany(office.get());
        return officeDto.getOrders().stream()
                .filter(orderDTOnoOffice -> orderDTOnoOffice
                        .getStatus()
                        .equals(deliveryStatus) &&
                        OrderDTOSenderReceiverWithIds
                                .get()
                                .getId()
                                .equals(clientId))
                .collect(Collectors.toList());
    }

 */
/*

    @Override
    public List<OrderDto> fetchOrdersByDeliveryStatusAndSenderId(Long officeId, DeliveryStatus deliveryStatus, Long clientId) throws OfficeNotFoundException, InvalidStatusException {
        Optional<Office> office = officeRepository.findById(officeId);
        if(!office.isPresent()){
            throw new OfficeNotFoundException("Office not available");
        }
        if(!DeliveryStatus.isValidStatus(deliveryStatus.name())){
            throw new InvalidStatusException("Invalid delivery status");
        }
        OfficeDto officeDto = entityMapper.mapToOfficeDTOnoCompany(office.get());
        return officeDto.getOrders().stream()
                .filter(orderDTOnoOffice -> orderDTOnoOffice
                        .getDeliveryStatus()
                        .equals(deliveryStatus) &&
                        orderDTOnoOffice
                                .getSender()
                                .getId()
                                .equals(clientId))
                .collect(Collectors.toList());
    }

 */

    /**
     * Saves an office to the repository. If the office is new, it is created; otherwise, the existing office is updated.
     *
     * @param office The {@link Office} entity to save.
     * @return The saved office as an {@link OfficeDto} without company information.
     */
    @Override
    public OfficeDto saveOffice(Office office) {
        return entityMapper.mapToOfficeDTOnoCompany(officeRepository.save(office));
    }
    /**
     * Retrieves a list of all offices, converted to {@link OfficeDto} without company information.
     *
     * @return A list of {@link OfficeDto} representing all offices.
     */
    @Override
    public List<OfficeDto> fetchOfficeList() {
        return entityMapper.mapOfficeListDTOnoCompany(officeRepository.findAll());
    }
    /**
     * Deletes an office by its ID.
     *
     * @param officeId The ID of the office to delete.
     * @throws OfficeNotFoundException if the office with the specified ID is not found.
     */
    @Override
    public void deleteOfficeById(Long officeId) throws OfficeNotFoundException {
        Optional<Office> office = officeRepository.findById(officeId);
        if(!office.isPresent()){
            throw new OfficeNotFoundException("Office not available!");
        }
        officeRepository.deleteById(officeId);
    }
    /**
     * Updates an existing office's details such as address and revenue.
     *
     * @param officeId The ID of the office to update.
     * @param office The new {@link Office} information to apply.
     * @return The updated office as an {@link OfficeDto} without company information.
     * @throws OfficeNotFoundException if the office with the specified ID is not found.
     */
    @Override
    public OfficeDto updateOffice(Long officeId, Office office) throws OfficeNotFoundException {
        Optional<Office> currOffice = officeRepository.findById(officeId);
        if(!currOffice.isPresent()){
            throw new OfficeNotFoundException("Office not available !");
        }
        Office officeDb=currOffice.get();

        if(Objects.nonNull(office.getAddress()) && !"".equals(office.getAddress())){
            officeDb.setAddress(office.getAddress());
        }
        if(Objects.nonNull(office.getRevenue()) && !"".equals(office.getRevenue())){
            officeDb.setRevenue(office.getRevenue());
        }

        return entityMapper.mapToOfficeDTOnoCompany(officeRepository.save(officeDb));
    }
    // needed together with fetchDefaultOffice() to create an order and then update the office's orders
    /**
     * Updates the orders of an office by adding a new order to its list of orders.
     *
     * @param newOrder The new {@link Order} to add to the office's orders.
     * @param fetchedDefaultOffice The office to which the order is to be added.
     * @return The updated office as an {@link OfficeDto} without company information.
     * @throws OfficeNotFoundException if the office is not found or if there are issues updating the office's orders.
     */
    @Override
    public OfficeDto updateOfficeOrders(Order newOrder, Office fetchedDefaultOffice) throws OfficeNotFoundException {

        // Ensure the orders list is initialized
        if (fetchedDefaultOffice.getOrders() == null) {
            fetchedDefaultOffice.setOrders(new ArrayList<>());
        }

        // Add the new order to the list
        fetchedDefaultOffice.getOrders().add(newOrder);

        // Save the updated office
        return entityMapper.mapToOfficeDTOnoCompany(officeRepository.save(fetchedDefaultOffice));
    }
    /**
     * Fetches a list of employees for a given office, sorted by their salary in ascending order.
     *
     * @param office The {@link Office} whose employees are to be fetched.
     * @return A list of {@link EmployeeDTO} representing the office's employees sorted by salary.
     */
    @Override
    public List<EmployeeDTO> fetchEmployeesSortedBySalary(Office office) {
        List<Employee> employees = office.getEmployees().stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .collect(Collectors.toList());
        return entityMapper.mapEmployeeListToDTO(employees);
    }

    /**
     * Retrieves a list of employees from a given office whose salary is above a specified amount.
     *
     * @param office The {@link Office} from which to fetch employees.
     * @param salary The salary threshold above which employees are to be included.
     * @return A list of {@link EmployeeDTO} representing employees with a salary above the specified amount.
     */
    @Override
    public List<EmployeeDTO> fetchEmployeesAboveSalary(Office office, double salary) {
        List<Employee> employees = office.getEmployees().stream()
                .filter(employee -> employee.getSalary() > salary)
                .collect(Collectors.toList());
        return entityMapper.mapEmployeeListToDTO(employees);
    }
    /**
     * Retrieves a list of employees from a given office whose salary is below a specified amount.
     *
     * @param office The {@link Office} from which to fetch employees.
     * @param salary The salary threshold below which employees are to be included.
     * @return A list of {@link EmployeeDTO} representing employees with a salary below the specified amount.
     */
    @Override
    public List<EmployeeDTO> fetchEmployeesBelowSalary(Office office, double salary) {
        List<Employee> employees = office.getEmployees().stream()
                .filter(employee -> employee.getSalary() < salary)
                .collect(Collectors.toList());
        return entityMapper.mapEmployeeListToDTO(employees);
    }

    /**
     * Fetches a list of employees from a specific office who have a matching name.
     *
     * @param office The {@link Office} from which to fetch employees.
     * @param name The name of the employees to be filtered by.
     * @return A list of {@link EmployeeDTO} representing employees whose names match the specified value.
     */
    @Override
    public List<EmployeeDTO> fetchEmployeesByName(Office office, String name) {
        List<Employee> employees = office.getEmployees().stream()
                .filter(employee -> employee.getName().equals(name))
                .collect(Collectors.toList());
        return entityMapper.mapEmployeeListToDTO(employees);
    }
    /**
     * Fetches a list of orders from a given office where the specified client is the sender.
     *
     * @param officeId The ID of the office to fetch orders from.
     * @param clientId The ID of the client who is the sender of the orders.
     * @return A list of {@link OrderDTOSenderReceiverWithIds} representing orders sent by the specified client.
     * @throws OfficeNotFoundException if the office with the specified ID is not found.
     */
    @Override
    public List<OrderDTOSenderReceiverWithIds> fetchClientListOfOrdersSender(Long officeId, Long clientId) throws OfficeNotFoundException {
        Optional<Office> office = officeRepository.findById(officeId);
        if(!office.isPresent()){
            throw new OfficeNotFoundException("Office not available");
        }
        OfficeDto officeDto = entityMapper.mapToOfficeDTOnoCompany(office.get());
        return officeDto.getOrders().stream()
                .filter(orderDTOnoOffice -> orderDTOnoOffice
                        .getSender()
                        .equals(clientId))
                .collect(Collectors.toList());

    }
    /**
     * Fetches a list of orders from a given office where the specified client is the receiver.
     *
     * @param officeId The ID of the office to fetch orders from.
     * @param clientId The ID of the client who is the receiver of the orders.
     * @return A list of {@link OrderDTOSenderReceiverWithIds} representing orders received by the specified client.
     * @throws OfficeNotFoundException if the office with the specified ID is not found.
     */
    @Override
    public List<OrderDTOSenderReceiverWithIds> fetchClientListOfOrdersReceiver(Long officeId, Long clientId) throws OfficeNotFoundException {
        Optional<Office> office = officeRepository.findById(officeId);
        if(!office.isPresent()){
            throw new OfficeNotFoundException("Office not available");
        }
        OfficeDto officeDto = entityMapper.mapToOfficeDTOnoCompany(office.get());
        return officeDto.getOrders().stream()
                .filter(orderDTOnoOffice ->
                             orderDTOnoOffice
                                .getReceiver()
                                .equals(clientId))
                .collect(Collectors.toList());

    }



}
