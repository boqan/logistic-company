package com.LogisticsCompany.repository;

import com.LogisticsCompany.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Custom methods if needed
}
