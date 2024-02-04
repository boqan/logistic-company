package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.dto.OfficeDto;
import com.LogisticsCompany.dto.OrderDto;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.InvalidStatusException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;

import java.util.List;

public interface OfficeService {

    List<OrderDto> fetchOrdersByDeliveryStatus(Long officeId, DeliveryStatus deliveryStatus) throws OfficeNotFoundException, InvalidStatusException;

    OfficeDto fetchOfficeById(Long officeId) throws OfficeNotFoundException;

    Office fetchOfficeByIdReturnsEntity(Long officeId) throws OfficeNotFoundException;
    List<OrderDto> fetchOrdersByDeliveryStatusAndReceiverId(Long officeId, DeliveryStatus deliveryStatus, Long clientId) throws OfficeNotFoundException, InvalidStatusException;

    List<OrderDto> fetchOrdersByDeliveryStatusAndSenderId(Long officeId, DeliveryStatus deliveryStatus, Long clientId) throws OfficeNotFoundException, InvalidStatusException;

    OfficeDto saveOffice(Office office);

    List<OfficeDto> fetchOfficeList();

    void deleteOfficeById(Long officeId) throws OfficeNotFoundException;

    OfficeDto updateOffice(Long officeId, Office office) throws OfficeNotFoundException;

    List<EmployeeDTO> fetchEmployeesSortedBySalary(Office office);

    OfficeDto updateOfficeOrders(Order newOrder, Office fetchedDefaultOffice) throws OfficeNotFoundException;

    List<EmployeeDTO> fetchEmployeesAboveSalary(Office office, double salary);

    List<EmployeeDTO> fetchEmployeesBelowSalary(Office office, double salary);

    List<EmployeeDTO> fetchEmployeesByName(Office office, String name);

    List<OrderDto> fetchClientListOfOrders(Long officeId, Long clientId) throws OfficeNotFoundException;
}
