package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.EmployeeDTOnoOffice;
import com.LogisticsCompany.dto.OfficeDTOnoCompany;
import com.LogisticsCompany.dto.OrderDTOnoOffice;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.InvalidStatusException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;

import java.util.List;

public interface OfficeService {

    List<OrderDTOnoOffice> fetchOrdersByDeliveryStatus(Long officeId, DeliveryStatus deliveryStatus) throws OfficeNotFoundException, InvalidStatusException;

    OfficeDTOnoCompany fetchOfficeById(Long officeId) throws OfficeNotFoundException;

    Office fetchDefaultOffice() throws OfficeNotFoundException;
    List<OrderDTOnoOffice> fetchOrdersByDeliveryStatusAndReceiverId(Long officeId, DeliveryStatus deliveryStatus, Long clientId) throws OfficeNotFoundException, InvalidStatusException;

    List<OrderDTOnoOffice> fetchOrdersByDeliveryStatusAndSenderId(Long officeId, DeliveryStatus deliveryStatus, Long clientId) throws OfficeNotFoundException, InvalidStatusException;

    Office saveOffice(Office office);

    List<Office> fetchOfficeList();

    void deleteOfficeById(Long officeId) throws OfficeNotFoundException;

    Office updateOffice(Long officeId, Office office) throws OfficeNotFoundException;

    Office updateOfficeOrders(Order newOrder, Office fetchedDefaultOffice) throws OfficeNotFoundException;

    List<EmployeeDTOnoOffice> fetchEmployeesSortedBySalary(Office office);

    List<EmployeeDTOnoOffice> fetchEmployeesAboveSalary(Office office, double salary);

    List<EmployeeDTOnoOffice> fetchEmployeesBelowSalary(Office office, double salary);

    List<EmployeeDTOnoOffice> fetchEmployeesByName(Office office, String name);

    List<OrderDTOnoOffice> fetchClientListOfOrders(Long officeId, Long clientId) throws OfficeNotFoundException;
}
