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

/**
 * Service class responsible for mapping between entities and DTOs.
 * It provides methods for converting entities to DTOs and vice versa, as well as lists of entities.
 */
@Service
public class EntityMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private OfficeRepository officeRepository;

    /**
     * Maps a LogisticCompany entity to a LogisticCompanyDto without including the company's offices.
     *
     * @param logisticCompany The LogisticCompany entity to be mapped.
     * @return A LogisticCompanyDto without offices.
     */
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
    /**
     * Maps an Office entity to an OfficeDto without including the company's details.
     *
     * @param office The Office entity to be mapped.
     * @return An OfficeDto without company details.
     */
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
    /**
     * Maps an Employee entity to an EmployeeDTO.
     *
     * @param employee The Employee entity to be mapped.
     * @return An EmployeeDTO.
     */
    public EmployeeDTO mapToDTOnoOffice(Employee employee){
        return modelMapper.map(employee, EmployeeDTO.class);
    }
    /**
     * Maps an Order entity to an OrderDto without including the office's details.
     *
     * @param order The Order entity to be mapped.
     * @return An OrderDto without office details.
     */
    public OrderDto mapToOrderDTOnoOffice(Order order){
        OrderDto orderDTO = modelMapper.map(order, OrderDto.class);

        orderDTO.setReceiver(mapClientToDTOnoOffice(order.getReceiver()));
        orderDTO.setSender(mapClientToDTOnoOffice(order.getSender()));
        return orderDTO;
    }
    /**
     * Maps a list of LogisticCompany entities to LogisticCompanyDto objects without including company details.
     *
     * @param logisticCompanies The list of LogisticCompany entities to be mapped.
     * @return A list of LogisticCompanyDto objects without company details.
     */
    public List<LogisticCompanyDto> mapLogisticCompanyListDTOnoCompany(List<LogisticCompany> logisticCompanies){
        return logisticCompanies.stream().map(this::mapToDTOLogisticsCompanyNoCompany).collect(Collectors.toList());
    }
    /**
     * Maps a list of Client entities to a list of ClientDTOs without including office details.
     *
     * @param clients The list of Client entities to be mapped.
     * @return A list of ClientDTOs without office details.
     */
    public List<ClientDTO> mapClientListDTOnoOffice(List<Client> clients){
        return clients.stream().map(this::mapClientToDTOnoOffice).collect(Collectors.toList());
    }
    /**
     * Maps a list of Order entities to a list of OrderDto objects without including office details.
     *
     * @param orders The list of Order entities to be mapped.
     * @return A list of OrderDto objects without office details.
     */
    public List<OrderDto> mapOrderListToDTOnoOffice(List<Order> orders){
        return orders.stream().map(this::mapToOrderDTOnoOffice).collect(Collectors.toList());
    }
    /**
     * Maps a list of Office entities to a list of OfficeDto objects without including company details.
     *
     * @param offices The list of Office entities to be mapped.
     * @return A list of OfficeDto objects without company details.
     */
    public List<OfficeDto> mapOfficeListDTOnoCompany(List<Office> offices){
        return offices.stream().map(this::mapToOfficeDTOnoCompany).collect(Collectors.toList());
    }
    /**
     * Maps a list of Employee entities to a list of EmployeeDTO objects.
     *
     * @param employees The list of Employee entities to be mapped.
     * @return A list of EmployeeDTO objects.
     */
    public List<EmployeeDTO> mapEmployeeListToDTO(List<Employee> employees){
        return employees.stream().map(this::mapToEmployeeDTO).collect(Collectors.toList());
    }
    /**
     * Maps an Employee entity to an EmployeeDTO.
     *
     * @param employee The Employee entity to be mapped.
     * @return An EmployeeDTO.
     */
    public EmployeeDTO mapToEmployeeDTO(Employee employee){
        return modelMapper.map(employee, EmployeeDTO.class);
    }
    /**
     * Converts a Client entity to a ClientDTO.
     *
     * @param clientEntity The Client entity to be converted.
     * @return A ClientDTO.
     */
    public ClientDTO convertToDto(Client clientEntity) {
        // Perform the mapping from ClientEntity to ClientDTOnoOffice
        ClientDTO clientDto = new ClientDTO();
        clientDto.setId(clientEntity.getId());
        clientDto.setName(clientEntity.getName());
        return clientDto;
    }
    /**
     * Converts a ClientDTO to a Client entity.
     *
     * @param clientDto The ClientDTO to be converted.
     * @return A Client entity.
     */
    public Client convertToClient(ClientDTO clientDto) {
        Client client = new Client();
        client.setName(clientDto.getName());
        client.setId(clientDto.getId());
        return client;
    }
    /**
     * Maps a Client entity to a ClientDTO without including office details.
     * Also, checks for the existence of the associated office.
     *
     * @param client The Client entity to be mapped.
     * @return A ClientDTO without office details.
     * @throws EntityNotFoundException If the associated office is not found.
     */
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
    /**
     * Converts a ClientDTO to a Client entity.
     * Also, checks for the existence of the associated office.
     *
     * @param clientDTO The ClientDTO to be converted.
     * @return A Client entity.
     * @throws EntityNotFoundException If the associated office is not found.
     */
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
    /**
     * Maps an Order entity to an OrderDTOSenderReceiverWithIds object.
     *
     * @param order The Order entity to be mapped.
     * @return An OrderDTOSenderReceiverWithIds object.
     */
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
    /**
     * Maps an OrderDTOSenderReceiverWithIds object to an Order entity.
     *
     * @param orderDTOSenderReceiverWithIds The OrderDTOSenderReceiverWithIds object to be mapped.
     * @return An Order entity.
     */
    public Order mapToOrderEntity(OrderDTOSenderReceiverWithIds orderDTOSenderReceiverWithIds) {
        return modelMapper.map(orderDTOSenderReceiverWithIds, Order.class);
    }
    /**
     * Maps a list of Order entities to a list of OrderDTOSenderReceiverWithIds objects.
     *
     * @param orders The list of Order entities to be mapped.
     * @return A list of OrderDTOSenderReceiverWithIds objects.
     */
    public List<OrderDTOSenderReceiverWithIds> mapToOrderDTOs(List<Order> orders) {
        return orders.stream()
                .map(this::mapToOrderDTOSenderReceiverWithIds)
                .collect(Collectors.toList());
    }
    /**
     * Maps an OrderCreationRequest to an Order entity builder.
     *
     * @param request The OrderCreationRequest to be mapped.
     * @return An Order entity builder.
     * @throws OrderCreationValidationException If there are validation errors in the request.
     */
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