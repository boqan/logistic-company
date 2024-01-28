package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.dto.ClientDTO;
import com.LogisticsCompany.enums.DeliveryType;
import com.LogisticsCompany.mapper.EntityMapper;
import com.LogisticsCompany.model.Client;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;
import com.LogisticsCompany.repository.ClientRepository;
import com.LogisticsCompany.service.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository repository;
    @Autowired
    private EntityMapper entityMapper;

    @Override
    public ClientDTO updateClient(ClientDTO clientDTO) {
        // Check if the clientDTO has a valid ID
        if (clientDTO.getId() == null) {
            throw new IllegalArgumentException("Client ID cannot be null for update.");
        }

        // Fetch the existing client from the database
        Client existingClient = repository.findById(clientDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + clientDTO.getId()));

        // Update all fields from the clientDTO
        entityMapper.updateClientFromDTO(existingClient, clientDTO);

        // Save the updated client back to the database
        Client updatedClient = repository.save(existingClient);

        // Map the updated client back to DTO
        return entityMapper.mapClientToDTO(updatedClient);
    }

    @Override
    public List<ClientDTO> getClients() {
        // Fetch all clients from the repository
        List<Client> clientEntities = repository.findAll();

        // Convert each ClientEntity to ClientDTOnoOffice using entityMapper
        return clientEntities.stream()
                .map(entityMapper::convertToDto)
                .collect(Collectors.toList());    }

    @Override
    public ClientDTO getClient(Long id) {
        Client client = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        ClientDTO clientDTO = entityMapper.convertToDto(client);
        return clientDTO;
    }

    @Override
    public void createClient(ClientDTO clientDto) {
        Client client = entityMapper.convertToClient(clientDto);
        repository.save(client);
    }

    @Override
    public void placeOrder(Order order, Office office, DeliveryType deliveryType) {

    }

    @Override
    public void payOrder(Order order) {

    }

    @Override
    public void receiveOrder(Order order) {

    }

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
