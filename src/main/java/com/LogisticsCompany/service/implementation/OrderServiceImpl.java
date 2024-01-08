package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.dto.OrderDTO;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.DeliveryStatusException;
import com.LogisticsCompany.mapper.EntityMapper;
import com.LogisticsCompany.model.Order;
import com.LogisticsCompany.repository.OrderRepository;
import com.LogisticsCompany.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final EntityMapper entityMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, EntityMapper entityMapper) {
        this.orderRepository = orderRepository;
        this.entityMapper = entityMapper;
    }

    // create two more overloads, with order order and long orderid, also add check if it already exists
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        orderRepository.findById(orderDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + orderDTO.getId()));

        Order order = entityMapper.mapToOrderEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);
        return entityMapper.mapToOrderDTO(savedOrder);
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + id));
        return entityMapper.mapToOrderDTO(order);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return entityMapper.mapToOrderDTOs(orders);
    }
    // same here
    @Override
    public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + orderId));

        // map from the DTO to the entity so that all fields are updated
        Order updatedOrder = entityMapper.mapToOrderEntity(orderDTO);
        existingOrder.setStatus(updatedOrder.getStatus());
        existingOrder.setWeight(updatedOrder.getWeight());
        existingOrder.setPrice(updatedOrder.getPrice());
        existingOrder.setReceiver(updatedOrder.getReceiver());
        existingOrder.setSender(updatedOrder.getSender());
        existingOrder.setOffice(updatedOrder.getOffice());
        existingOrder.setReceiverAddress(updatedOrder.getReceiverAddress());
        existingOrder.setDeliveryType(updatedOrder.getDeliveryType());

        // save the updated existing order
        orderRepository.save(existingOrder);

        // return the updated existing order as a DTO
        return entityMapper.mapToOrderDTO(existingOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + id));
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteOrder(OrderDTO orderDTO) {
        orderRepository.findById(orderDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + orderDTO.getId()));
        orderRepository.deleteById(orderDTO.getId());
    }

    @Override
    public void changeOrderStatus(Long orderId, DeliveryStatus newStatus) throws DeliveryStatusException {
        // If this order exists in the DB, update its status
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + orderId + " does not exist in the database"));

        if (order.getStatus() == DeliveryStatus.DELIVERED) {
            throw new DeliveryStatusException("Order with id " + orderId + " has already been delivered");
        }
        if (order.getStatus() == newStatus) {
            throw new DeliveryStatusException("Order with id " + orderId + " already has status " + newStatus);
        }

        order.setStatus(newStatus);
        orderRepository.save(order);
    }
    //overload to acceptOrderDTO
    @Override
    public void changeOrderStatus(OrderDTO orderDTO, DeliveryStatus newStatus) throws DeliveryStatusException {
        // If this order exists in the DB, update its status
        Order order = orderRepository.findById(orderDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + orderDTO.getId() + " does not exist in the database"));

        if (order.getStatus() == DeliveryStatus.DELIVERED) {
            throw new DeliveryStatusException("Order with id " + orderDTO.getId() + " has already been delivered");
        }
        if (order.getStatus() == newStatus) {
            throw new DeliveryStatusException("Order with id " + orderDTO.getId() + " already has status " + newStatus);
        }

        order.setStatus(newStatus);
        orderRepository.save(order);
    }

    @Override
    public double calculateOrderPrice(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + orderId + " does not exist in the database"));
        // This is a placeholder for the calculation logic
        // base price will be calculated as 10 percent of the price of the order
        double basePrice = order.getPrice() * 0.1;
        double weightFactor = order.getWeight();
        double distanceFactor = 5.0; // Example distance factor, implement logic to calculate distance.

        return basePrice + (order.getWeight() * weightFactor) + distanceFactor;
    }

    //overload to accept orderDTO
    @Override
    public double calculateOrderPrice(OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + orderDTO.getId() + " does not exist in the database"));

        // This is a placeholder for the calculation logic
        // base price will be calculated as 10 percent of the price of the order
        double basePrice = order.getPrice() * 0.1;
        double weightFactor = order.getWeight();
        double distanceFactor = 5.0; // Example distance factor, implement logic to calculate distance.

        return basePrice + (order.getWeight() * weightFactor) + distanceFactor;
    }
}