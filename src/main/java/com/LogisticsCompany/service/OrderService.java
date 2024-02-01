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
    OrderDTOSenderReceiverWithIds createOrder(OrderCreationRequest request) throws OrderCreationValidationException, EntityAlreadyExistsInDbException, OfficeNotFoundException;
    OrderDTOSenderReceiverWithIds getOrderById(Long id);
    List<OrderDTOSenderReceiverWithIds> getAllOrders();
    OrderDTOSenderReceiverWithIds updateOrder(Long orderId, OrderUpdateRequest orderUpdateRequest);
    void deleteOrder(Long id); // accepts orderId
    void deleteOrder(OrderDTOSenderReceiverWithIds orderDTOSenderReceiverWithIds); // same method - overload to accept orderDTO
    void changeOrderStatus(Long orderId, DeliveryStatus newStatus) throws DeliveryStatusException; // accepts orderId
    void changeOrderStatus(OrderDTOSenderReceiverWithIds orderDTOSenderReceiverWithIds, DeliveryStatus newStatus) throws DeliveryStatusException; // same method - overload to accept orderDTO

    double calculateOrderPriceForOrderCreation(OrderCreationRequest request);
    double calculateOrderPriceForExistingOrder(Long orderId); // accepts orderID
    double calculateOrderPriceForExistingOrder(OrderDTOSenderReceiverWithIds orderDTOSenderReceiverWithIds); // same method - overload to accept orderDTO
}
