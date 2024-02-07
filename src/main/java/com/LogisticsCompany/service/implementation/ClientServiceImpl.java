package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.dto.ClientDTO;
import com.LogisticsCompany.enums.DeliveryType;
import com.LogisticsCompany.mapper.EntityMapper;
import com.LogisticsCompany.model.Client;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;
import com.LogisticsCompany.repository.ClientRepository;
import com.LogisticsCompany.repository.OfficeRepository;
import com.LogisticsCompany.service.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Service implementation for managing client-related operations.
 * This class provides methods to update, retrieve, create, and delete client entities.
 * It also includes methods for placing, paying, and receiving orders related to clients.
 *
 * @see org.springframework.stereotype.Service
 */
@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository repository;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private EntityMapper entityMapper;
    /**
     * Update a client's information based on the provided ClientDTO and client ID.
     *
     * @param clientDTO The updated client information in the form of a ClientDTO.
     * @param clientId  The ID of the client to be updated.
     * @return The updated ClientDTO after the update operation.
     * @throws EntityNotFoundException If the client with the given ID is not found in the database.
     */
    @Override
    public ClientDTO updateClient(ClientDTO clientDTO , Long clientId) {


        // Fetch the existing client from the database
        Client existingClient = repository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + clientDTO.getId()));

        Long newOfficeId = clientDTO.getOfficeId();
        Office newOffice = officeRepository.findById(newOfficeId)
                .orElseThrow(() -> new EntityNotFoundException("Office with ID " + newOfficeId + " is not found"));

        existingClient.setName(clientDTO.getName());
        existingClient.setOffice(newOffice);
        // Save the updated client back to the database
        existingClient = repository.save(existingClient);

        // Map the updated client back to DTO
        return entityMapper.mapClientToDTOnoOffice(existingClient);
    }
    /**
     * Get a list of all clients.
     *
     * @return A list of ClientDTOs representing all clients in the database.
     */
    @Override
    public List<ClientDTO> getClients() {
        // Fetch all clients from the repository
        List<Client> clientEntities = repository.findAll();

        // Convert each ClientEntity to ClientDTOnoOffice using entityMapper
        return clientEntities.stream()
                .map(entityMapper::mapClientToDTOnoOffice)
                .collect(Collectors.toList());    }
    /**
     * Get a client by ID.
     *
     * @param id The ID of the client to retrieve.
     * @return The ClientDTO representing the client with the given ID.
     * @throws EntityNotFoundException If the client with the given ID is not found in the database.
     */
    @Override
    public ClientDTO getClient(Long id) {
        Client client = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        ClientDTO clientDTO = entityMapper.mapClientToDTOnoOffice(client);
        return clientDTO;
    }
    /**
     * Create a new client based on the provided ClientDTO.
     *
     * @param clientDto The ClientDTO containing the information for the new client.
     * @return The ClientDTO representing the newly created client.
     */
    @Override
    public ClientDTO createClient(ClientDTO clientDto) {
        Client client = entityMapper.mapDTOToClient(clientDto);
        return entityMapper.mapClientToDTOnoOffice(repository.save(client));
    }

    @Override
    public void placeOrder(Order order, Office office, DeliveryType deliveryType) {

    }

    @Override
    public void payOrder(Order order) {
        BigDecimal price = BigDecimal.valueOf(order.getPrice());
        order.getOffice().setRevenue(order.getOffice().getRevenue().add(price));
    }

    @Override
    public void receiveOrder(Order order) {

    }
    /**
     * Delete a client by ID.
     *
     * @param clientId The ID of the client to delete.
     * @return True if the client is successfully deleted, false if the client does not exist.
     */
    @Override
    public boolean deleteClient(Long clientId){
        try{
            getClient(clientId);
        }catch (Exception e){
            return false;
        }
        repository.deleteById(clientId);
        return true;
    }

}