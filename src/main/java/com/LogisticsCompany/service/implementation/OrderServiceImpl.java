package com.LogisticsCompany.service.implementation;

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
import com.LogisticsCompany.service.ClientService;
import com.LogisticsCompany.service.OfficeService;
import com.LogisticsCompany.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Service implementation for handling order-related operations. This class offers functionality to create, retrieve,
 * update, and delete orders, as well as to change their status and calculate their price.
 * It interacts with various repositories and services to perform its duties, including client and office management,
 * and order price calculation based on certain criteria.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final ClientService clientService;
    private final OrderRepository orderRepository;

    private final ClientRepository clientRepository;

    private final OfficeRepository officeRepository;
    private final EntityMapper entityMapper;

    private final OfficeService officeService;

    @Autowired
    public OrderServiceImpl(ClientService clientService, OrderRepository orderRepository, ClientRepository clientRepository, OfficeRepository officeRepository, EntityMapper entityMapper, OfficeService officeService) {
        this.clientService = clientService;
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.officeRepository = officeRepository;
        this.entityMapper = entityMapper;
        this.officeService = officeService;
    }
    /**
     * Validates the order creation request details.
     *
     * @param request The order creation request to validate.
     * @throws OrderCreationValidationException if any validation checks fail (e.g., negative weight, null fields).
     */
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
    /**
     * Creates a new order based on the provided order creation request and office ID.
     *
     * @param request The order creation request.
     * @param officeId The ID of the office from which the order is sent.
     * @return An OrderDTOSenderReceiverWithIds representation of the created order.
     * @throws OrderCreationValidationException if the request validation fails.
     * @throws OfficeNotFoundException if the specified office is not found.
     */
    @Override
    public OrderDTOSenderReceiverWithIds createOrder(OrderCreationRequest request, Long officeId) throws OrderCreationValidationException, OfficeNotFoundException {
        // Validate order creation request
        orderCreationRequestValidation(request);

        Order.OrderBuilder orderBuilder = entityMapper.mapOrderCreationRequestToEntity(request); // part of the mapping logic

        orderBuilder.price(calculateOrderPriceForOrderCreation(request)); // price is calculated based on the request

        Office fetchedOffice = officeService.fetchOfficeByIdReturnsEntity(officeId); // gets the office provided in the URL path and sets that as the office from which it is being sent
        orderBuilder.office(fetchedOffice); // the first office is set as the office for the order

        orderBuilder.sender(clientRepository.findById(request.sender()).get());

        orderBuilder.receiver(clientRepository.findById(request.receiver()).get());

        Order order = orderBuilder.build(); // build the order entity

        officeService.updateOfficeOrders(order, fetchedOffice); // update the office's orders (add the new order to the list of orders)

        Order savedOrder = orderRepository.save(order);

        clientService.payOrder(order);

        return entityMapper.mapToOrderDTOSenderReceiverWithIds(savedOrder);
    }
    /**
     * Retrieves an order by its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return An OrderDTOSenderReceiverWithIds representation of the order.
     * @throws EntityNotFoundException if no order is found for the given ID.
     */
    @Override
    public OrderDTOSenderReceiverWithIds getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + id));
        return entityMapper.mapToOrderDTOSenderReceiverWithIds(order);
    }
    /**
     * Retrieves all orders.
     *
     * @return A list of OrderDTOSenderReceiverWithIds representing all orders.
     */
    @Override
    public List<OrderDTOSenderReceiverWithIds> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return entityMapper.mapToOrderDTOs(orders);
    }
    // same here
    /**
     * Updates an order based on the provided order ID and update request.
     *
     * @param orderId The ID of the order to update.
     * @param orderUpdateRequest The request containing the order updates.
     * @return An OrderDTOSenderReceiverWithIds representation of the updated order.
     * @throws EntityNotFoundException if no order is found for the given ID or if specified sender/receiver/office is not found.
     */
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

    /**
     * Deletes an order by its ID.
     *
     * @param id The ID of the order to delete.
     * @throws EntityNotFoundException if no order is found for the given ID.
     */
    @Override
    public void deleteOrder(Long id) throws EntityNotFoundException {
        orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + id));
        orderRepository.deleteById(id);
    }
    /**
     * Deletes an order based on the provided OrderDTOSenderReceiverWithIds.
     *
     * @param orderDTOSenderReceiverWithIds The DTO of the order to delete.
     * @throws EntityNotFoundException if no order is found for the ID contained within the DTO.
     */
    @Override
    public void deleteOrder(OrderDTOSenderReceiverWithIds orderDTOSenderReceiverWithIds) throws EntityNotFoundException{
        orderRepository.findById(orderDTOSenderReceiverWithIds.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + orderDTOSenderReceiverWithIds.getId()));


        orderRepository.deleteById(orderDTOSenderReceiverWithIds.getId());
    }
    /**
     * Changes the status of an existing order.
     *
     * @param orderId The ID of the order whose status is to be changed.
     * @param newStatus The new status to apply to the order.
     * @throws EntityNotFoundException if the order does not exist.
     * @throws DeliveryStatusException if the order has already been delivered or if the order already has the desired status.
     */
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
    /**
     * Overloaded method to change the status of an existing order based on OrderDTO.
     *
     * @param orderDTOSenderReceiverWithIds The DTO of the order whose status is to be changed.
     * @param newStatus The new status to apply to the order.
     * @throws DeliveryStatusException Directly uses the changeOrderStatus(Long, DeliveryStatus) method for operation.
     */
    @Override
    public void changeOrderStatus(OrderDTOSenderReceiverWithIds orderDTOSenderReceiverWithIds, DeliveryStatus newStatus) throws DeliveryStatusException {
        changeOrderStatus(orderDTOSenderReceiverWithIds.getId(), newStatus);
    }
    /**
     * Calculates the price for an order at the creation stage based on the provided request.
     *
     * @param request The order creation request.
     * @return The calculated price for the order.
     */
    @Override
    public double calculateOrderPriceForOrderCreation(OrderCreationRequest request) {

        // This is a placeholder for the calculation logic
        // base price will be a set number
        double basePrice = 5;
        double weightFactor = request.weight() * 0.2;
        double distanceFactor = 2.0; // Example distance factor, implement logic to calculate distance.

        return basePrice + (request.weight() * weightFactor) + distanceFactor;
    }
    /**
     * Calculates the price for an existing order based on its ID.
     *
     * @param orderId The ID of the order for which the price is to be calculated.
     * @return The calculated price for the order.
     * @throws EntityNotFoundException if the order does not exist.
     */
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
    /**
     * Overloaded method to calculate the price for an existing order based on an OrderDTO.
     *
     * @param orderDTOSenderReceiverWithIds The DTO of the order for which the price is to be calculated.
     * @return The calculated price for the order.
     * @throws EntityNotFoundException if the order does not exist.
     */
    @Override
    public double calculateOrderPriceForExistingOrder(OrderDTOSenderReceiverWithIds orderDTOSenderReceiverWithIds) {
        return calculateOrderPriceForExistingOrder(orderDTOSenderReceiverWithIds.getId());
    }
}