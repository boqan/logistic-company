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

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping ("/{officeId}")// implement exception handler for these exceptions
    public ResponseEntity<OrderDTOSenderReceiverWithIds> createOrder(@RequestBody OrderCreationRequest request, @PathVariable Long officeId)
            throws OrderCreationValidationException, EntityAlreadyExistsInDbException, OfficeNotFoundException
    {
        OrderDTOSenderReceiverWithIds createdOrder = orderService.createOrder(request, officeId);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // there is also the same get method in logistic company controller
//    @GetMapping("/{orderId}")
//    public ResponseEntity<OrderDTOSenderReceiverWithIds> getOrderById(@PathVariable Long orderId) {
//        OrderDTOSenderReceiverWithIds orderDTOSenderReceiverWithIds = orderService.getOrderById(orderId);
//        return ResponseEntity.ok(orderDTOSenderReceiverWithIds);
//    }

    @GetMapping
    public ResponseEntity<List<OrderDTOSenderReceiverWithIds>> getAllOrders() {
        List<OrderDTOSenderReceiverWithIds> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTOSenderReceiverWithIds> updateOrder(@PathVariable Long id, @RequestBody OrderUpdateRequest orderUpdateRequest) {
        OrderDTOSenderReceiverWithIds updatedOrder = orderService.updateOrder(id, orderUpdateRequest);
        return ResponseEntity.ok(updatedOrder);
    }
    // also implement exception handler for this exception
    @DeleteMapping("/{id}") //implement order deletion from the list of the office for this order
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) throws EntityNotFoundException {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}/status") //implement the changing of the order status in the list of the office as well
    public ResponseEntity<Void> changeOrderStatus(@PathVariable Long id, @RequestParam DeliveryStatus newStatus) throws DeliveryStatusException {
        orderService.changeOrderStatus(id, newStatus);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/pricecalc")
    public ResponseEntity<Double> calculateOrderPrice(@PathVariable Long id) {
        double price = orderService.calculateOrderPriceForExistingOrder(id);
        return ResponseEntity.ok(price);
    }

}