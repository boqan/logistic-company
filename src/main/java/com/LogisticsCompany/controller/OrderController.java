package com.LogisticsCompany.controller;

import com.LogisticsCompany.dto.OrderCreationRequest;
import com.LogisticsCompany.dto.OrderDTOnoOfficeSenderRecieverWithIds;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.DeliveryStatusException;
import com.LogisticsCompany.error.EntityAlreadyExistsInDbException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.error.OrderCreationValidationException;
import com.LogisticsCompany.service.OrderService;
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

    @PostMapping // implement exception handler for these exceptions
    public ResponseEntity<OrderDTOnoOfficeSenderRecieverWithIds> createOrder(@RequestBody OrderCreationRequest request) throws OrderCreationValidationException, EntityAlreadyExistsInDbException, OfficeNotFoundException {
        OrderDTOnoOfficeSenderRecieverWithIds createdOrder = orderService.createOrder(request);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTOnoOfficeSenderRecieverWithIds> getOrderById(@PathVariable Long id) {
        OrderDTOnoOfficeSenderRecieverWithIds orderDTOnoOfficeSenderRecieverWithIds = orderService.getOrderById(id);
        return ResponseEntity.ok(orderDTOnoOfficeSenderRecieverWithIds);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTOnoOfficeSenderRecieverWithIds>> getAllOrders() {
        List<OrderDTOnoOfficeSenderRecieverWithIds> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTOnoOfficeSenderRecieverWithIds> updateOrder(@PathVariable Long id, @RequestBody OrderDTOnoOfficeSenderRecieverWithIds orderDTOnoOfficeSenderRecieverWithIds) {
        OrderDTOnoOfficeSenderRecieverWithIds updatedOrder = orderService.updateOrder(id, orderDTOnoOfficeSenderRecieverWithIds);
        return ResponseEntity.ok(updatedOrder);
    }
    // also implement exception handler for this exception
    @DeleteMapping("/{id}") //implement order deletion from the list of the office for this order
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    // implement exception handler for this exception
    @PatchMapping("/{id}/status") //implement the changing of the order status in the list of the office as well
    public ResponseEntity<Void> changeOrderStatus(@PathVariable Long id, @RequestParam DeliveryStatus newStatus) throws DeliveryStatusException {
        orderService.changeOrderStatus(id, newStatus);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/calculatePrice_existingOrder")
    public ResponseEntity<Double> calculateOrderPrice(@PathVariable Long id) {
        double price = orderService.calculateOrderPriceForExistingOrder(id);
        return ResponseEntity.ok(price);
    }

}