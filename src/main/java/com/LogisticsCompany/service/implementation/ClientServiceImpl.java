//package com.LogisticsCompany.service.implementation;
//
//import com.LogisticsCompany.dto.ClientDTOnoOffice;
//import com.LogisticsCompany.enums.DeliveryType;
//import com.LogisticsCompany.mapper.EntityMapper;
//import com.LogisticsCompany.model.Client;
//import com.LogisticsCompany.model.Office;
//import com.LogisticsCompany.model.Order;
//import com.LogisticsCompany.repository.ClientRepository;
//import com.LogisticsCompany.service.ClientService;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class ClientServiceImpl implements ClientService {
//    @Autowired
//    private ClientRepository repository;
//    @Autowired
//    private EntityMapper entityMapper;
//    @Override
//    public List<ClientDTOnoOffice> getClients() {
//        // Fetch all clients from the repository
//        List<Client> clientEntities = repository.findAll();
//
//        // Convert each ClientEntity to ClientDTOnoOffice using entityMapper
//        return clientEntities.stream()
//                .map(entityMapper::convertToDto)
//                .collect(Collectors.toList());    }
//
//    @Override
//    public ClientDTOnoOffice getClient(Long id) {
//        Client client = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
//        ClientDTOnoOffice clientDTO = entityMapper.convertToDto(client);
//        return clientDTO;
//    }
//
//    @Override
//    public void placeOrder(Order order, Office office, DeliveryType deliveryType) {
//
//    }
//
//    @Override
//    public void payOrder(Order order) {
//
//    }
//
//    @Override
//    public void receiveOrder(Order order) {
//
//    }
//
//}
