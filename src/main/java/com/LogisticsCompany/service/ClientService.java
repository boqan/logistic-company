package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.ClientDTO;
import com.LogisticsCompany.enums.DeliveryType;
import com.LogisticsCompany.model.Client;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;

import java.util.List;


public interface ClientService {
    List<ClientDTO> getClients();
    ClientDTO getClient(Long id);
    void createClient(ClientDTO client);

    ClientDTO updateClient(ClientDTO clientDTO,Long clientId);

    void placeOrder(Order order, Office office, DeliveryType deliveryType);
    void payOrder(Order order);     //always called when requesting an order - logic for increasing revenue for the office
    void receiveOrder(Order order);     //change the delivery status to DELIVERED

    boolean deleteClient(Long clientId);
}
