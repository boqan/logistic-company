package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.model.Order;
import com.LogisticsCompany.model.DeliveryStatus;
import com.LogisticsCompany.repository.OrderRepository;
import com.LogisticsCompany.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        // If this order exists in the DB, update its status
        Optional<Order> orderOptional = orderRepository.findById(order.getId());
        if (orderOptional.isEmpty()) {
            throw new EntityNotFoundException("Order with id " + order.getId() + " does not exist in the database");
        }
        if(order.getStatus() == DeliveryStatus.DELIVERED){
            throw new IllegalArgumentException("Order with id " + order.getId() + " has already been delivered");
        }
        if(order.getStatus() == newStatus){
            throw new IllegalArgumentException("Order with id " + order.getId() + " already has status " + newStatus);
        }
        order.setStatus(newStatus);
        orderRepository.save(order);
    }

    @Override
    public double calculateOrderPrice(Order order) {
        // This is a placeholder for the calculation logic
        // base price will be calculated as 10 percent of the price of the order
        double basePrice = order.getPrice() * 0.1;
        double weightFactor = order.getWeight();
        double distanceFactor = 5.0; // Example distance factor, implement logic to calculate distance.

        return basePrice + (order.getWeight() * weightFactor) + distanceFactor;
    }
}