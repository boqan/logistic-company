package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.OrderCreationRequest;
import com.LogisticsCompany.dto.OrderDTOnoOfficeSenderRecieverWithIds;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.DeliveryStatusException;
import com.LogisticsCompany.error.EntityAlreadyExistsInDbException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.error.OrderCreationValidationException;

import java.util.List;

public interface OrderService {
    OrderDTOnoOfficeSenderRecieverWithIds createOrder(OrderCreationRequest request, Long officeId) throws OrderCreationValidationException, EntityAlreadyExistsInDbException, OfficeNotFoundException;
    OrderDTOnoOfficeSenderRecieverWithIds getOrderById(Long id);
    List<OrderDTOnoOfficeSenderRecieverWithIds> getAllOrders();
    OrderDTOnoOfficeSenderRecieverWithIds updateOrder(Long orderId, OrderDTOnoOfficeSenderRecieverWithIds orderDTOnoOfficeSenderRecieverWithIds);
    void deleteOrder(Long id); // accepts orderId
    void deleteOrder(OrderDTOnoOfficeSenderRecieverWithIds orderDTOnoOfficeSenderRecieverWithIds); // same method - overload to accept orderDTO
    void changeOrderStatus(Long orderId, DeliveryStatus newStatus) throws DeliveryStatusException; // accepts orderId
    void changeOrderStatus(OrderDTOnoOfficeSenderRecieverWithIds orderDTOnoOfficeSenderRecieverWithIds, DeliveryStatus newStatus) throws DeliveryStatusException; // same method - overload to accept orderDTO

    double calculateOrderPriceForOrderCreation(OrderCreationRequest request);
    double calculateOrderPriceForExistingOrder(Long orderId); // accepts orderID
    double calculateOrderPriceForExistingOrder(OrderDTOnoOfficeSenderRecieverWithIds orderDTOnoOfficeSenderRecieverWithIds); // same method - overload to accept orderDTO
}
