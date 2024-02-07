package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.ClientDTO;
import com.LogisticsCompany.enums.DeliveryType;
import com.LogisticsCompany.model.Client;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;

import java.util.List;


public interface ClientService {
    /**
     * Retrieves a list of all clients.
     *
     * @return a list of {@link ClientDTO} objects representing all clients.
     */
    List<ClientDTO> getClients();
    /**
     * Retrieves a specific client by their ID.
     *
     * @param id the ID of the client to retrieve.
     * @return the {@link ClientDTO} of the requested client.
     */
    ClientDTO getClient(Long id);
    /**
     * Creates a new client.
     *
     * @param client the {@link ClientDTO} object containing the client's information.
     * @return the {@link ClientDTO} representing the newly created client.
     */
    ClientDTO createClient(ClientDTO client);
    /**
     * Updates the information for an existing client.
     *
     * @param clientDTO the {@link ClientDTO} object containing the updated client information.
     * @param clientId the ID of the client to update.
     * @return the {@link ClientDTO} representing the updated client.
     */
    ClientDTO updateClient(ClientDTO clientDTO,Long clientId);
    /**
     * Places an order for a client, associating it with a specific office and delivery type.
     *
     * @param order the {@link Order} object to be placed.
     * @param office the {@link Office} where the order is placed.
     * @param deliveryType the {@link DeliveryType} of the order.
     */

    void placeOrder(Order order, Office office, DeliveryType deliveryType);
    /**
     * Pays for an order. This method is always called when requesting an order and contains
     * logic for increasing the revenue for the associated office.
     *
     * @param order the {@link Order} object that is being paid.
     */
    void payOrder(Order order);     //always called when requesting an order - logic for increasing revenue for the office
    /**
     * Receives an order, changing its delivery status to DELIVERED.
     *
     * @param order the {@link Order} object to be received.
     */
    void receiveOrder(Order order);     //change the delivery status to DELIVERED
    /**
     * Deletes a client by their ID.
     *
     * @param clientId the ID of the client to delete.
     * @return {@code true} if the client was successfully deleted; {@code false} otherwise.
     */

    boolean deleteClient(Long clientId);
}
