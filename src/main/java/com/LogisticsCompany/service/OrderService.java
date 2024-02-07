package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.OrderCreationRequest;
import com.LogisticsCompany.dto.OrderDTOSenderReceiverWithIds;
import com.LogisticsCompany.dto.OrderUpdateRequest;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.DeliveryStatusException;
import com.LogisticsCompany.error.EntityAlreadyExistsInDbException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.error.OrderCreationValidationException;

import java.util.List;

public interface OrderService {
    /**
     * Creates a new order based on the provided request and associates it with an office.
     *
     * @param request the request details for creating the order.
     * @param officeId the ID of the office to which the order is associated.
     * @return An {@link OrderDTOSenderReceiverWithIds} object representing the newly created order.
     * @throws OrderCreationValidationException if the order creation request fails validation checks.
     * @throws EntityAlreadyExistsInDbException if the order already exists in the database.
     * @throws OfficeNotFoundException if the specified office is not found.
     */
    OrderDTOSenderReceiverWithIds createOrder(OrderCreationRequest request, Long officeId) throws OrderCreationValidationException, EntityAlreadyExistsInDbException, OfficeNotFoundException;
    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order to retrieve.
     * @return An {@link OrderDTOSenderReceiverWithIds} object of the requested order.
     */
    OrderDTOSenderReceiverWithIds getOrderById(Long id);
    /**
     * Retrieves all existing orders.
     *
     * @return A list of {@link OrderDTOSenderReceiverWithIds} objects representing all orders.
     */
    List<OrderDTOSenderReceiverWithIds> getAllOrders();
    /**
     * Updates an existing order with the provided details.
     *
     * @param orderId the ID of the order to update.
     * @param orderUpdateRequest the details to update the order with.
     * @return An {@link OrderDTOSenderReceiverWithIds} object representing the updated order.
     */
    OrderDTOSenderReceiverWithIds updateOrder(Long orderId, OrderUpdateRequest orderUpdateRequest);
    /**
     * Deletes an order by its ID.
     *
     * @param id the ID of the order to delete.
     */
    void deleteOrder(Long id); // accepts orderId
    /**
     * Deletes an order based on the provided order DTO.
     *
     * @param orderDTOSenderReceiverWithIds the DTO of the order to delete.
     */
    void deleteOrder(OrderDTOSenderReceiverWithIds orderDTOSenderReceiverWithIds); // same method - overload to accept orderDTO
    /**
     * Changes the status of an order identified by its ID.
     *
     * @param orderId the ID of the order whose status is to be changed.
     * @param newStatus the new status to apply to the order.
     * @throws DeliveryStatusException if changing the order status is not permissible.
     */
    void changeOrderStatus(Long orderId, DeliveryStatus newStatus) throws DeliveryStatusException; // accepts orderId
    /**
     * Changes the status of an order based on the provided order DTO.
     *
     * @param orderDTOSenderReceiverWithIds the DTO of the order whose status is to be changed.
     * @param newStatus the new status to apply to the order.
     * @throws DeliveryStatusException if changing the order status is not permissible.
     */
    void changeOrderStatus(OrderDTOSenderReceiverWithIds orderDTOSenderReceiverWithIds, DeliveryStatus newStatus) throws DeliveryStatusException; // same method - overload to accept orderDTO
    /**
     * Calculates the price for a new order based on the provided creation request.
     *
     * @param request the order creation request to calculate the price for.
     * @return the calculated price as a double.
     */
    double calculateOrderPriceForOrderCreation(OrderCreationRequest request);
    /**
     * Calculates the price for an existing order identified by its ID.
     *
     * @param orderId the ID of the order to calculate the price for.
     * @return the calculated price as a double.
     */
    double calculateOrderPriceForExistingOrder(Long orderId); // accepts orderID

    /**
     * Calculates the price for an existing order based on the provided order DTO.
     *
     * @param orderDTOSenderReceiverWithIds the DTO of the order to calculate the price for.
     * @return the calculated price as a double.
     */
    double calculateOrderPriceForExistingOrder(OrderDTOSenderReceiverWithIds orderDTOSenderReceiverWithIds); // same method - overload to accept orderDTO
}
