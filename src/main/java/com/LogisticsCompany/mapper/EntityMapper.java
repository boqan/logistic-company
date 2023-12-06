package com.LogisticsCompany.mapper;
import com.LogisticsCompany.dto.*;
import com.LogisticsCompany.model.*;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntityMapper {
    private final ModelMapper modelMapper= new ModelMapper();


    public LogisticCompanyDTOnoCompany mapToDTOLogisticsCompanyNoCompany(LogisticCompany logisticCompany){
        LogisticCompanyDTOnoCompany companyDTO=modelMapper.map(logisticCompany, LogisticCompanyDTOnoCompany.class);
        companyDTO.setEmployees(mapEmployeeListToDTOnoCompany(logisticCompany.getEmployees()));
        companyDTO.setOffices(mapOfficeListDTOnoCompany(logisticCompany.getOffices()));
        companyDTO.setClients(mapClientListDTOnoCompany(logisticCompany.getClients()));
        return companyDTO;
    }

    public OfficeDTOnoCompany mapToOfficeDTOnoCompany(Office office){
        OfficeDTOnoCompany officeDto =modelMapper.map(office, OfficeDTOnoCompany.class);
        officeDto.setOrders(mapOrderListToDTOnoOffice(office.getOrders()));
        return officeDto;
    }


    public EmployeeDTOnoCompany mapToDTOnoCompany(Employee employee){
        return modelMapper.map(employee, EmployeeDTOnoCompany.class);
    }

    public OrderDTOnoOffice mapToDTOnoOffice(Order order){
        return modelMapper.map(order, OrderDTOnoOffice.class);
    }

    public ClientDTOnoCompany mapClientToDTOnoCompany(Client client){
        return modelMapper.map(client,ClientDTOnoCompany.class);
    }

    private List<ClientDTOnoCompany> mapClientListDTOnoCompany(List<Client> clients){
        return clients.stream().map(this::mapClientToDTOnoCompany).collect(Collectors.toList());
    }

    private List<OrderDTOnoOffice> mapOrderListToDTOnoOffice(List<Order> orders){
        return orders.stream().map(this::mapToDTOnoOffice).collect(Collectors.toList());
    }

    private List<OfficeDTOnoCompany> mapOfficeListDTOnoCompany(List<Office> offices){
        return offices.stream().map(this::mapToOfficeDTOnoCompany).collect(Collectors.toList());
    }

    private List<EmployeeDTOnoCompany> mapEmployeeListToDTOnoCompany(List<Employee> employees){
        return employees.stream().map(this::mapToDTOnoCompany).collect(Collectors.toList());
    }

}










