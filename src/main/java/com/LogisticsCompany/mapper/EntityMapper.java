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

    public LogisticCompanyDTOnoOffice mapToDTOLogisticsCompanyNoCompany(LogisticCompany logisticCompany){
        LogisticCompanyDTOnoOffice companyDTO=modelMapper.map(logisticCompany, LogisticCompanyDTOnoOffice.class);
        companyDTO.setOffices(mapOfficeListDTOnoCompany(logisticCompany.getOffices()));
        return companyDTO;
    }

    public OfficeDTOnoCompany mapToOfficeDTOnoCompany(Office office){
        OfficeDTOnoCompany officeDto = modelMapper.map(office, OfficeDTOnoCompany.class);
        officeDto.setOrders(mapOrderListToDTOnoOffice(office.getOrders()));
        officeDto.setEmployees(mapEmployeeListToDTOnoOffice(office.getEmployees()));
        officeDto.setClients(mapClientListDTOnoOffice(office.getClients()));
        return officeDto;
    }

    public EmployeeDTOnoOffice mapToDTOnoOffice(Employee employee){
        return modelMapper.map(employee, EmployeeDTOnoOffice.class);
    }

    public OrderDTOnoOffice mapToOrderDTOnoOffice(Order order){
        OrderDTOnoOffice orderDTO = modelMapper.map(order, OrderDTOnoOffice.class);
        orderDTO.setReceiver(mapClientToDTOnoOffice(order.getReceiver()));
        orderDTO.setSender(mapClientToDTOnoOffice(order.getSender()));
        return orderDTO;
    }

    public ClientDTO mapClientToDTOnoOffice(Client client){
        return modelMapper.map(client, ClientDTO.class);
    }

    private List<ClientDTO> mapClientListDTOnoOffice(List<Client> clients){
        return clients.stream().map(this::mapClientToDTOnoOffice).collect(Collectors.toList());
    }

    private List<OrderDTOnoOffice> mapOrderListToDTOnoOffice(List<Order> orders){
        return orders.stream().map(this::mapToOrderDTOnoOffice).collect(Collectors.toList());
    }

    public List<OfficeDTOnoCompany> mapOfficeListDTOnoCompany(List<Office> offices){
        return offices.stream().map(this::mapToOfficeDTOnoCompany).collect(Collectors.toList());
    }

    public List<EmployeeDTOnoOffice> mapEmployeeListToDTOnoOffice(List<Employee> employees){
        return employees.stream().map(this::mapToDTOnoOffice).collect(Collectors.toList());
    }

}










