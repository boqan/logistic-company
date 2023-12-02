package com.LogisticsCompany.service;

import com.LogisticsCompany.model.Order;
import com.LogisticsCompany.model.DeliveryStatus;
import com.LogisticsCompany.repository.OrderRepository;
import com.LogisticsCompany.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void changeOrderStatus(Order order, DeliveryStatus newStatus) {
        // Check for any business rules or validations before changing the status
        order.setStatus(newStatus);
        orderRepository.save(order); // Persist the change to the database
    }

    @Override
    public double calculateOrderPrice(Order order) {
        // Implement the logic to calculate the price based on order details
        // This is a placeholder for the calculation logic
        double basePrice = 10.0; // Example base price
        double weightFactor = 2.0; // Example weight factor
        double distanceFactor = 5.0; // Example distance factor
        // The actual price calculation will depend on your business logic
        return basePrice + (order.getWeight() * weightFactor) + distanceFactor;
    }
}