package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.dto.OrderCreationRequest;
import com.LogisticsCompany.dto.OrderDTOnoOfficeSenderRecieverWithIds;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.DeliveryStatusException;
import com.LogisticsCompany.error.EntityAlreadyExistsInDbException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.error.OrderCreationValidationException;
import com.LogisticsCompany.mapper.EntityMapper;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;
import com.LogisticsCompany.repository.OrderRepository;
import com.LogisticsCompany.service.OfficeService;
import com.LogisticsCompany.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final EntityMapper entityMapper;

    private final OfficeService officeService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, EntityMapper entityMapper, OfficeService officeService) {
        this.orderRepository = orderRepository;
        this.entityMapper = entityMapper;
        this.officeService = officeService;
    }

    private void orderCreationRequestValidation(OrderCreationRequest request) throws OrderCreationValidationException {
        if(request.weight() <= 0){
            throw new OrderCreationValidationException("Weight must be greater than 0");
        }
        if(request.deliveryType() == null){
            throw new OrderCreationValidationException("Delivery type must be specified");
        }

        if(request.receiver() == null){
            throw new OrderCreationValidationException("Receiver must be specified");
        }
        if(request.sender() == null){
            throw new OrderCreationValidationException("Sender must be specified");
        }
        if(request.receiverAddress() == null){
            throw new OrderCreationValidationException("Receiver address must be specified");
        }

    }
    @Override
    public OrderDTOnoOfficeSenderRecieverWithIds createOrder(OrderCreationRequest request, Long officeId) throws OrderCreationValidationException, EntityAlreadyExistsInDbException, OfficeNotFoundException {
        // Validate order creation request
        orderCreationRequestValidation(request);

        Order.OrderBuilder orderBuilder = entityMapper.mapOrderCreationRequestToEntity(request); // part of the mapping logic

        orderBuilder.price(calculateOrderPriceForOrderCreation(request)); // price is calculated based on the request

        Office fetchedDefaultOffice = officeService.fetchOfficeByIdReturnsEntity(officeId); // gets the office provided in the URL path and sets that as the office from which it is being sent
        orderBuilder.office(fetchedDefaultOffice); // the first office is set as the office for the order

        Order order = orderBuilder.build(); // build the order entity
// DA SE TESTVA
//        officeService.updateOfficeOrders(order, fetchedDefaultOffice); // update the office's orders (add the new order to the list of orders)

        Order savedOrder = orderRepository.save(order);
        return entityMapper.mapToOrderDTOnoOfficeSenderRecieverWithIds(savedOrder);
    }

    @Override
    public OrderDTOnoOfficeSenderRecieverWithIds getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + id));
        return entityMapper.mapToOrderDTOnoOfficeSenderRecieverWithIds(order);
    }

    @Override
    public List<OrderDTOnoOfficeSenderRecieverWithIds> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return entityMapper.mapToOrderDTOs(orders);
    }
    // same here
    @Override
    public OrderDTOnoOfficeSenderRecieverWithIds updateOrder(Long orderId, OrderDTOnoOfficeSenderRecieverWithIds orderDTOnoOfficeSenderRecieverWithIds) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + orderId));

        // map from the DTO to the entity so that all fields are updated
        Order updatedOrder = entityMapper.mapToOrderEntity(orderDTOnoOfficeSenderRecieverWithIds);
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
        return entityMapper.mapToOrderDTOnoOfficeSenderRecieverWithIds(existingOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + id));
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteOrder(OrderDTOnoOfficeSenderRecieverWithIds orderDTOnoOfficeSenderRecieverWithIds) {
        orderRepository.findById(orderDTOnoOfficeSenderRecieverWithIds.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + orderDTOnoOfficeSenderRecieverWithIds.getId()));


        orderRepository.deleteById(orderDTOnoOfficeSenderRecieverWithIds.getId());
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
    public void changeOrderStatus(OrderDTOnoOfficeSenderRecieverWithIds orderDTOnoOfficeSenderRecieverWithIds, DeliveryStatus newStatus) throws DeliveryStatusException {
        changeOrderStatus(orderDTOnoOfficeSenderRecieverWithIds.getId(), newStatus);
    }

    @Override
    public double calculateOrderPriceForOrderCreation(OrderCreationRequest request) {

        // This is a placeholder for the calculation logic
        // base price will be a set number
        double basePrice = 5;
        double weightFactor = request.weight() * 0.2;
        double distanceFactor = 2.0; // Example distance factor, implement logic to calculate distance.

        return basePrice + (request.weight() * weightFactor) + distanceFactor;
    }

    @Override
    public double calculateOrderPriceForExistingOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + orderId + " does not exist in the database"));
        // This is a placeholder for the calculation logic
        // base price will be a set number
        double basePrice = 5;
        double weightFactor = order.getWeight() * 0.2;
        double distanceFactor = 2.0; // Example distance factor, implement logic to calculate distance.

        return basePrice + (order.getWeight() * weightFactor) + distanceFactor;
    }

    //overload to accept orderDTO
    @Override
    public double calculateOrderPriceForExistingOrder(OrderDTOnoOfficeSenderRecieverWithIds orderDTOnoOfficeSenderRecieverWithIds) {
        return calculateOrderPriceForExistingOrder(orderDTOnoOfficeSenderRecieverWithIds.getId());
    }
}