package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.dto.OfficeDto;
import com.LogisticsCompany.dto.OrderCreationRequest;
import com.LogisticsCompany.dto.OrderDTOSenderReceiverWithIds;
import com.LogisticsCompany.dto.OrderUpdateRequest;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.DeliveryStatusException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.error.OrderCreationValidationException;
import com.LogisticsCompany.mapper.EntityMapper;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.model.Order;
import com.LogisticsCompany.repository.ClientRepository;
import com.LogisticsCompany.repository.OfficeRepository;
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

    private final ClientRepository clientRepository;

    private final OfficeRepository officeRepository;
    private final EntityMapper entityMapper;

    private final OfficeService officeService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ClientRepository clientRepository, OfficeRepository officeRepository, EntityMapper entityMapper, OfficeService officeService) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.officeRepository = officeRepository;
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
    public OrderDTOSenderReceiverWithIds createOrder(OrderCreationRequest request, Long officeId) throws OrderCreationValidationException, OfficeNotFoundException {
        // Validate order creation request
        orderCreationRequestValidation(request);

        Order.OrderBuilder orderBuilder = entityMapper.mapOrderCreationRequestToEntity(request); // part of the mapping logic

        orderBuilder.price(calculateOrderPriceForOrderCreation(request)); // price is calculated based on the request

        Office fetchedOffice = officeService.fetchOfficeByIdReturnsEntity(officeId); // gets the office provided in the URL path and sets that as the office from which it is being sent
        orderBuilder.office(fetchedOffice); // the first office is set as the office for the order

        Order order = orderBuilder.build(); // build the order entity

        officeService.updateOfficeOrders(order, fetchedOffice); // update the office's orders (add the new order to the list of orders)

        Order savedOrder = orderRepository.save(order);
        return entityMapper.mapToOrderDTOSenderReceiverWithIds(savedOrder);
    }

    @Override
    public OrderDTOSenderReceiverWithIds getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + id));
        return entityMapper.mapToOrderDTOSenderReceiverWithIds(order);
    }

    @Override
    public List<OrderDTOSenderReceiverWithIds> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return entityMapper.mapToOrderDTOs(orders);
    }
    // same here
    @Override
    public OrderDTOSenderReceiverWithIds updateOrder(Long orderId, OrderUpdateRequest orderUpdateRequest) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + orderId));

        if (orderUpdateRequest.getSender() != null) {
            existingOrder.setSender(clientRepository.findById(orderUpdateRequest.getSender())
                    .orElseThrow(() -> new EntityNotFoundException("Sender not found for ID: " + orderUpdateRequest.getSender())));
        }
        if (orderUpdateRequest.getReceiver() != null) {
            existingOrder.setReceiver(clientRepository.findById(orderUpdateRequest.getReceiver())
                    .orElseThrow(() -> new EntityNotFoundException("Receiver not found for ID: " + orderUpdateRequest.getReceiver())));
        }
        if (orderUpdateRequest.getOfficeId() != null) {
            existingOrder.setOffice(officeRepository.findById(orderUpdateRequest.getOfficeId())
                    .orElseThrow(() -> new EntityNotFoundException("Office not found for ID: " + orderUpdateRequest.getOfficeId())));
        }

        existingOrder.setWeight(orderUpdateRequest.getWeight());
        existingOrder.setReceiverAddress(orderUpdateRequest.getReceiverAddress());
        existingOrder.setPrice(orderUpdateRequest.getPrice());
        existingOrder.setDeliveryType(orderUpdateRequest.getDeliveryType());
        existingOrder.setStatus(orderUpdateRequest.getStatus());

        // save the updated existing order
        orderRepository.save(existingOrder);

        // return the updated existing order as a DTO
        return entityMapper.mapToOrderDTOSenderReceiverWithIds(existingOrder);
    }


    @Override
    public void deleteOrder(Long id) throws EntityNotFoundException {
        orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + id));
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteOrder(OrderDTOSenderReceiverWithIds orderDTOSenderReceiverWithIds) throws EntityNotFoundException{
        orderRepository.findById(orderDTOSenderReceiverWithIds.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + orderDTOSenderReceiverWithIds.getId()));


        orderRepository.deleteById(orderDTOSenderReceiverWithIds.getId());
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
    public void changeOrderStatus(OrderDTOSenderReceiverWithIds orderDTOSenderReceiverWithIds, DeliveryStatus newStatus) throws DeliveryStatusException {
        changeOrderStatus(orderDTOSenderReceiverWithIds.getId(), newStatus);
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
    public double calculateOrderPriceForExistingOrder(OrderDTOSenderReceiverWithIds orderDTOSenderReceiverWithIds) {
        return calculateOrderPriceForExistingOrder(orderDTOSenderReceiverWithIds.getId());
    }
}