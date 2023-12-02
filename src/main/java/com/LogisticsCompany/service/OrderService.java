package com.LogisticsCompany.service;

import com.LogisticsCompany.model.Order;
import com.LogisticsCompany.model.DeliveryStatus;

import java.util.List;

public interface OrderService {
    void changeOrderStatus(Order order, DeliveryStatus newStatus);
    double calculateOrderPrice(Order order);
}
