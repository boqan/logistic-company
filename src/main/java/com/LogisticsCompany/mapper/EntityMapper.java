package com.LogisticsCompany.mapper;
import com.LogisticsCompany.dto.*;
import com.LogisticsCompany.model.*;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntityMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public LogisticCompanyDto mapToDTOLogisticsCompanyNoCompany(LogisticCompany logisticCompany){
        LogisticCompanyDto companyDTO=modelMapper.map(logisticCompany, LogisticCompanyDto.class);
        companyDTO.setOffices(mapOfficeListDTOnoCompany(logisticCompany.getOffices()));
        return companyDTO;
    }

    public OfficeDto mapToOfficeDTOnoCompany(Office office){
        OfficeDto officeDto = modelMapper.map(office, OfficeDto.class);
        officeDto.setOrders(mapOrderListToDTOnoOffice(office.getOrders()));
        officeDto.setEmployees(mapEmployeeListToDTOnoOffice(office.getEmployees()));
        officeDto.setClients(mapClientListDTOnoOffice(office.getClients()));
        return officeDto;
    }

    public EmployeeDto mapToDTOnoOffice(Employee employee){
        return modelMapper.map(employee, EmployeeDto.class);
    }

    public OrderDto mapToOrderDTOnoOffice(Order order){
        OrderDto orderDTO = modelMapper.map(order, OrderDto.class);
        orderDTO.setReceiver(mapClientToDTOnoOffice(order.getReceiver()));
        orderDTO.setSender(mapClientToDTOnoOffice(order.getSender()));
        return orderDTO;
    }

    public ClientDto mapClientToDTOnoOffice(Client client){
        return modelMapper.map(client, ClientDto.class);
    }

    private List<ClientDto> mapClientListDTOnoOffice(List<Client> clients){
        return clients.stream().map(this::mapClientToDTOnoOffice).collect(Collectors.toList());
    }

    private List<OrderDto> mapOrderListToDTOnoOffice(List<Order> orders){
        return orders.stream().map(this::mapToOrderDTOnoOffice).collect(Collectors.toList());
    }

    public List<OfficeDto> mapOfficeListDTOnoCompany(List<Office> offices){
        return offices.stream().map(this::mapToOfficeDTOnoCompany).collect(Collectors.toList());
    }

    public List<EmployeeDto> mapEmployeeListToDTOnoOffice(List<Employee> employees){
        return employees.stream().map(this::mapToDTOnoOffice).collect(Collectors.toList());
    }

}










