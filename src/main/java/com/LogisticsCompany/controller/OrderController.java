package com.LogisticsCompany.controller;

import com.LogisticsCompany.dto.OrderCreationRequest;
import com.LogisticsCompany.dto.OrderDTOSenderReceiverWithIds;
import com.LogisticsCompany.dto.OrderUpdateRequest;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.DeliveryStatusException;
import com.LogisticsCompany.error.EntityAlreadyExistsInDbException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.error.OrderCreationValidationException;
import com.LogisticsCompany.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller for handling operations related to orders within the logistics system.
 * Supports creating, retrieving, updating, deleting, and changing the status of orders.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;


    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    /**
     * Creates a new order based on the given request and associates it with an office.
     *
     * @param request the order creation request containing order details
     * @param officeId the ID of the office to associate the order with
     * @return a ResponseEntity containing the created order details and HTTP status CREATED
     * @throws OrderCreationValidationException if the order data fails validation checks
     * @throws EntityAlreadyExistsInDbException if an order with similar attributes already exists
     * @throws OfficeNotFoundException if the specified office does not exist
     */
    @PostMapping ("/{officeId}")// implement exception handler for these exceptions
    public ResponseEntity<OrderDTOSenderReceiverWithIds> createOrder(@RequestBody OrderCreationRequest request, @PathVariable Long officeId)
            throws OrderCreationValidationException, EntityAlreadyExistsInDbException, OfficeNotFoundException
    {
        OrderDTOSenderReceiverWithIds createdOrder = orderService.createOrder(request, officeId);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }
    /**
     * Retrieves an order by its ID.
     *
     * @param orderId the ID of the order to retrieve
     * @return a ResponseEntity containing the order details
     */
    // there is also the same get method in logistic company controller
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTOSenderReceiverWithIds> getOrderById(@PathVariable Long orderId) {
        OrderDTOSenderReceiverWithIds orderDTOSenderReceiverWithIds = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderDTOSenderReceiverWithIds);
    }
    /**
     * Retrieves all orders.
     *
     * @return a ResponseEntity containing a list of all orders
     */
    @GetMapping
    public ResponseEntity<List<OrderDTOSenderReceiverWithIds>> getAllOrders() {
        List<OrderDTOSenderReceiverWithIds> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    /**
     * Updates an existing order with new information.
     *
     * @param id the ID of the order to update
     * @param orderUpdateRequest the new order information
     * @return a ResponseEntity containing the updated order details
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTOSenderReceiverWithIds> updateOrder(@PathVariable Long id, @RequestBody OrderUpdateRequest orderUpdateRequest) {
        OrderDTOSenderReceiverWithIds updatedOrder = orderService.updateOrder(id, orderUpdateRequest);
        return ResponseEntity.ok(updatedOrder);
    }
    /**
     * Deletes an order by its ID.
     *
     * @param id the ID of the order to delete
     * @return a ResponseEntity with no content
     * @throws EntityNotFoundException if the order cannot be found
     */
    // also implement exception handler for this exception
    @DeleteMapping("/{id}") //implement order deletion from the list of the office for this order
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) throws EntityNotFoundException {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    /**
     * Changes the status of an existing order.
     *
     * @param id the ID of the order whose status is to be changed
     * @param newStatus the new status for the order
     * @return a ResponseEntity with OK status
     * @throws DeliveryStatusException if the new status is not valid for the order
     */
    @PatchMapping("/{id}/status") //implement the changing of the order status in the list of the office as well
    public ResponseEntity<Void> changeOrderStatus(@PathVariable Long id, @RequestParam DeliveryStatus newStatus) throws DeliveryStatusException {
        orderService.changeOrderStatus(id, newStatus);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/{id}/pricecalc")
//    public ResponseEntity<Double> calculateOrderPrice(@PathVariable Long id) {
//        double price = orderService.calculateOrderPriceForExistingOrder(id);
//        return ResponseEntity.ok(price);
//    }

}