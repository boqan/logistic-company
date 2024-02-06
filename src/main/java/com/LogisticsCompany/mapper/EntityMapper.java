package com.LogisticsCompany.mapper;
import com.LogisticsCompany.dto.*;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.enums.DeliveryType;
import com.LogisticsCompany.error.OrderCreationValidationException;
import com.LogisticsCompany.model.*;
import com.LogisticsCompany.repository.OfficeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntityMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private OfficeRepository officeRepository;

    public LogisticCompanyDto mapToDTOLogisticsCompanyNoCompany(LogisticCompany logisticCompany){
        //LogisticCompanyDto companyDTO=modelMapper.map(logisticCompany, LogisticCompanyDto.class);
        LogisticCompanyDto companyDTO = new LogisticCompanyDto();
        companyDTO.setId(logisticCompany.getId());
        companyDTO.setName(logisticCompany.getName());
        companyDTO.setCountry(logisticCompany.getCountry());
        companyDTO.setOffices(mapOfficeListDTOnoCompany(logisticCompany.getOffices()));
        companyDTO.setOffices(mapOfficeListDTOnoCompany(logisticCompany.getOffices()));
        return companyDTO;
    }

    public OfficeDto mapToOfficeDTOnoCompany(Office office){

        //OfficeDto officeDto = modelMapper.map(office, OfficeDto.class);
        OfficeDto officeDto = new OfficeDto();
        officeDto.setId(office.getId());
        if(office.getLogisticCompany()!=null){
            officeDto.setCompanyId(office.getLogisticCompany().getId());
        }
        officeDto.setAddress(office.getAddress());
        officeDto.setOrders(mapToOrderDTOs(office.getOrders()));
        officeDto.setEmployees(mapEmployeeListToDTO(office.getEmployees()));
        officeDto.setClients(mapClientListDTOnoOffice(office.getClients()));
        if(office.getLogisticCompany() != null) {
            officeDto.setCompanyId(office.getLogisticCompany().getId());
        }
        return officeDto;
    }

    public EmployeeDTO mapToDTOnoOffice(Employee employee){
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public OrderDto mapToOrderDTOnoOffice(Order order){
        OrderDto orderDTO = modelMapper.map(order, OrderDto.class);

        orderDTO.setReceiver(mapClientToDTOnoOffice(order.getReceiver()));
        orderDTO.setSender(mapClientToDTOnoOffice(order.getSender()));
        return orderDTO;
    }



    public List<LogisticCompanyDto> mapLogisticCompanyListDTOnoCompany(List<LogisticCompany> logisticCompanies){
        return logisticCompanies.stream().map(this::mapToDTOLogisticsCompanyNoCompany).collect(Collectors.toList());
    }

    public List<ClientDTO> mapClientListDTOnoOffice(List<Client> clients){
        return clients.stream().map(this::mapClientToDTOnoOffice).collect(Collectors.toList());
    }

    public List<OrderDto> mapOrderListToDTOnoOffice(List<Order> orders){
        return orders.stream().map(this::mapToOrderDTOnoOffice).collect(Collectors.toList());
    }

    public List<OfficeDto> mapOfficeListDTOnoCompany(List<Office> offices){
        return offices.stream().map(this::mapToOfficeDTOnoCompany).collect(Collectors.toList());
    }

    public List<EmployeeDTO> mapEmployeeListToDTO(List<Employee> employees){
        return employees.stream().map(this::mapToEmployeeDTO).collect(Collectors.toList());
    }

    public EmployeeDTO mapToEmployeeDTO(Employee employee){
        return modelMapper.map(employee, EmployeeDTO.class);
    }
    public ClientDTO convertToDto(Client clientEntity) {
        // Perform the mapping from ClientEntity to ClientDTOnoOffice
        ClientDTO clientDto = new ClientDTO();
        clientDto.setId(clientEntity.getId());
        clientDto.setName(clientEntity.getName());
        return clientDto;
    }
    public Client convertToClient(ClientDTO clientDto) {
        Client client = new Client();
        client.setName(clientDto.getName());
        client.setId(clientDto.getId());
        return client;
    }

    public ClientDTO mapClientToDTOnoOffice(Client client){
        ClientDTO clientDTO = modelMapper.map(client, ClientDTO.class);

        if (clientDTO.getOfficeId() != null) {
            // Retrieve the Office from the repository using the officeId
            Optional<Office> officeOptional = officeRepository.findById(clientDTO.getOfficeId());

            // Check if the office exists in the database
            if (officeOptional.isPresent()) {
                client.setOffice(officeOptional.get());
            } else {
                // Handle the case where the office is not found
                throw new EntityNotFoundException("Office with ID " + clientDTO.getOfficeId() + " is not found");
            }
        }
        return modelMapper.map(client, ClientDTO.class);
    }

    public Client mapDTOToClient(ClientDTO clientDTO){
        Client client = modelMapper.map(clientDTO, Client.class);
        if (clientDTO.getOfficeId() != null) {
            // Retrieve the Office from the repository using the officeId
            Optional<Office> officeOptional = officeRepository.findById(clientDTO.getOfficeId());

            // Check if the office exists in the database
            if (officeOptional.isPresent()) {
                client.setOffice(officeOptional.get());
            } else {
                // Handle the case where the office is not found
                throw new EntityNotFoundException("Office with ID " + clientDTO.getOfficeId() + " is not found");
            }
            return client;
        }
        else throw new  EntityNotFoundException();
    }

    // Order mappings-----------------------------------------------------------------------------------------------
    public OrderDTOSenderReceiverWithIds mapToOrderDTOSenderReceiverWithIds(Order order) {
        if (order == null) {
            return null;
        }

        OrderDTOSenderReceiverWithIds dto = new OrderDTOSenderReceiverWithIds();
        dto.setId(order.getId());
        dto.setSender(order.getSender() != null ? order.getSender().getId() : null);
        dto.setReceiver(order.getReceiver() != null ? order.getReceiver().getId() : null);
        dto.setWeight(order.getWeight());
        dto.setReceiverAddress(order.getReceiverAddress());
        dto.setPrice(order.getPrice());
        dto.setDeliveryType(order.getDeliveryType());
        dto.setStatus(order.getStatus());
        dto.setOfficeId(order.getOffice() != null ? order.getOffice().getId() : null);

        return dto;
    }

    public Order mapToOrderEntity(OrderDTOSenderReceiverWithIds orderDTOSenderReceiverWithIds) {
        return modelMapper.map(orderDTOSenderReceiverWithIds, Order.class);
    }

    public List<OrderDTOSenderReceiverWithIds> mapToOrderDTOs(List<Order> orders) {
        return orders.stream()
                .map(this::mapToOrderDTOSenderReceiverWithIds)
                .collect(Collectors.toList());
    }

    public Order.OrderBuilder mapOrderCreationRequestToEntity(OrderCreationRequest request) throws OrderCreationValidationException {
        Order.OrderBuilder orderBuilder = Order.builder();

        try {
            DeliveryType deliveryType = DeliveryType.valueOf(request.deliveryType().toUpperCase());
            orderBuilder.deliveryType(deliveryType); // Store the converted delivery type enum in the order entity builder
        } catch (IllegalArgumentException e) {
            throw new OrderCreationValidationException("Invalid delivery type: " + request.deliveryType());
        }

        return orderBuilder
                .weight(request.weight())
                .receiverAddress(request.receiverAddress())
                .status(DeliveryStatus.PENDING);

    }
}