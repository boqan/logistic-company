package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.OrderDTO;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.DeliveryStatusException;
import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO getOrderById(Long id);
    List<OrderDTO> getAllOrders();
    OrderDTO updateOrder(Long orderId, OrderDTO orderDTO);
    void deleteOrder(Long id); // accepts orderId
    void deleteOrder(OrderDTO orderDTO); // same method - overload to accept orderDTO
    void changeOrderStatus(Long orderId, DeliveryStatus newStatus) throws DeliveryStatusException; // accepts orderId
    void changeOrderStatus(OrderDTO orderDTO, DeliveryStatus newStatus) throws DeliveryStatusException; // same method - overload to accept orderDTO
    double calculateOrderPrice(Long orderId); // accepts orderID
    double calculateOrderPrice(OrderDTO orderDTO); // same method - overload to accept orderDTO
}
