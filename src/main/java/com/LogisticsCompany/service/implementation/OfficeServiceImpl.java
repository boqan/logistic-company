package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.dto.EmployeeDTO;
import com.LogisticsCompany.dto.OfficeDto;
import com.LogisticsCompany.dto.OrderDto;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.InvalidStatusException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.mapper.EntityMapper;
import com.LogisticsCompany.model.Employee;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.repository.OfficeRepository;
import com.LogisticsCompany.service.OfficeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfficeServiceImpl implements OfficeService {

    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private EntityMapper entityMapper;

    @Override   // no need to use custom OfficeNotFoundException we have a default one for all entities
    public OfficeDto fetchOfficeById(Long officeId) throws OfficeNotFoundException {
        Office office = officeRepository.findById(officeId).orElseThrow(() -> new EntityNotFoundException());
        OfficeDto officeDto = entityMapper.mapToOfficeDTOnoCompany(office);
        return officeDto;
    }


    @Override
    public List<OrderDto> fetchOrdersByDeliveryStatus(Long officeId, DeliveryStatus deliveryStatus) throws OfficeNotFoundException, InvalidStatusException {
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
                        .getDeliveryStatus()
                        .equals(deliveryStatus))
                        .collect(Collectors.toList());
    }


    @Override
    public List<OrderDto> fetchOrdersByDeliveryStatusAndReceiverId(Long officeId, DeliveryStatus deliveryStatus, Long clientId) throws OfficeNotFoundException, InvalidStatusException {
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
                                .getReceiver()
                                .getId()
                                .equals(clientId))
                .collect(Collectors.toList());
    }


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

    @Override
    public Office saveOffice(Office office) {
        return officeRepository.save(office);
    }

    @Override
    public List<Office> fetchOfficeList() {
        return officeRepository.findAll();
    }

    @Override
    public void deleteOfficeById(Long officeId) throws OfficeNotFoundException {
        Optional<Office> office = officeRepository.findById(officeId);
        if(!office.isPresent()){
            throw new OfficeNotFoundException("Office not available!");
        }
        officeRepository.deleteById(officeId);
    }

    @Override
    public Office updateOffice(Long officeId, Office office) throws OfficeNotFoundException {
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

        return officeRepository.save(officeDb);
    }

    @Override
    public List<EmployeeDTO> fetchEmployeesSortedBySalary(Office office) {
        List<Employee> employees = office.getEmployees().stream()
                .sorted((e1, e2) -> (int) (e1.getSalary() - e2.getSalary()))
                .collect(Collectors.toList());
        return entityMapper.mapEmployeeListToDTOnoOffice(employees);
    }


    @Override
    public List<EmployeeDTO> fetchEmployeesAboveSalary(Office office, double salary) {
        List<Employee> employees = office.getEmployees().stream()
                .filter(employee -> employee.getSalary() > salary)
                .collect(Collectors.toList());
        return entityMapper.mapEmployeeListToDTOnoOffice(employees);
    }

    @Override
    public List<EmployeeDTO> fetchEmployeesBelowSalary(Office office, double salary) {
        List<Employee> employees = office.getEmployees().stream()
                .filter(employee -> employee.getSalary() < salary)
                .collect(Collectors.toList());
        return entityMapper.mapEmployeeListToDTOnoOffice(employees);
    }


    @Override
    public List<EmployeeDTO> fetchEmployeesByName(Office office, String name) {
        List<Employee> employees = office.getEmployees().stream()
                .filter(employee -> employee.getName().equals(name))
                .collect(Collectors.toList());
        return entityMapper.mapEmployeeListToDTOnoOffice(employees);
    }

    @Override
    public List<OrderDto> fetchClientListOfOrders(Long officeId, Long clientId) throws OfficeNotFoundException {
        Optional<Office> office = officeRepository.findById(officeId);
        if(!office.isPresent()){
            throw new OfficeNotFoundException("Office not available");
        }
        OfficeDto officeDto = entityMapper.mapToOfficeDTOnoCompany(office.get());
        return officeDto.getOrders().stream()
                .filter(orderDTOnoOffice -> orderDTOnoOffice
                        .getSender()
                        .getId()
                        .equals(clientId) ||
                        orderDTOnoOffice
                                .getReceiver()
                                .getId()
                                .equals(clientId))
                .collect(Collectors.toList());

    }

}
