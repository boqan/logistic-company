package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.ClientDTOnoOffice;
import com.LogisticsCompany.enums.DeliveryType;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;

import java.util.List;


public interface ClientService {
    List<ClientDTOnoOffice> getClients();
    ClientDTOnoOffice getClient(Long id);

    void placeOrder(Order order, Office office, DeliveryType deliveryType);
    void payOrder(Order order);     //always called when requesting an order - logic for increasing revenue for the office
    void receiveOrder(Order order);     //change the delivery status to DELIVERED



}
