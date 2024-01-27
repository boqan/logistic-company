package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.dto.OfficeDto;
import com.LogisticsCompany.dto.OrderDto;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.InvalidStatusException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.model.Office;

import java.math.BigDecimal;
import java.util.List;

public interface OfficeService {

    List<OrderDto> fetchOrdersByDeliveryStatus(Long officeId, DeliveryStatus deliveryStatus) throws OfficeNotFoundException, InvalidStatusException;

    OfficeDto fetchOfficeById(Long officeId) throws OfficeNotFoundException;

    List<OrderDto> fetchOrdersByDeliveryStatusAndReceiverId(Long officeId, DeliveryStatus deliveryStatus, Long clientId) throws OfficeNotFoundException, InvalidStatusException;

    List<OrderDto> fetchOrdersByDeliveryStatusAndSenderId(Long officeId, DeliveryStatus deliveryStatus, Long clientId) throws OfficeNotFoundException, InvalidStatusException;

    Office saveOffice(Office office);

    List<Office> fetchOfficeList();

    void deleteOfficeById(Long officeId) throws OfficeNotFoundException;

    Office updateOffice(Long officeId, Office office) throws OfficeNotFoundException;

    List<EmployeeDTO> fetchEmployeesSortedBySalary(Office office);

    List<EmployeeDTO> fetchEmployeesAboveSalary(Office office, double salary);

    List<EmployeeDTO> fetchEmployeesBelowSalary(Office office, double salary);

    List<EmployeeDTO> fetchEmployeesByName(Office office, String name);

    List<OrderDto> fetchClientListOfOrders(Long officeId, Long clientId) throws OfficeNotFoundException;
}
