package com.LogisticsCompany.mapper;
import com.LogisticsCompany.dto.*;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.enums.DeliveryType;
import com.LogisticsCompany.error.OrderCreationValidationException;
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
        officeDto.setEmployees(mapEmployeeListToDTO(office.getEmployees()));
        officeDto.setClients(mapClientListDTOnoOffice(office.getClients()));
        return officeDto;
    }

    public EmployeeDTO mapToDTOnoOffice(Employee employee){
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public OrderDto mapToOrderDTOnoOffice(Order order){
        OrderDto orderDTO = modelMapper.map(order, OrderDto.class);

        orderDTO.setReceiver(mapClientToDTO(order.getReceiver()));
        orderDTO.setSender(mapClientToDTO(order.getSender()));
        return orderDTO;
    }

    public ClientDTO mapClientToDTO(Client client){
        return modelMapper.map(client, ClientDTO.class);
    }

    private List<ClientDTO> mapClientListDTOnoOffice(List<Client> clients){
        return clients.stream().map(this::mapClientToDTO).collect(Collectors.toList());
    }

    private List<OrderDto> mapOrderListToDTOnoOffice(List<Order> orders){
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
    public void updateClientFromDTO(Client client, ClientDTO clientDTO) {
        // Update all fields from ClientDTO to Client
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
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










