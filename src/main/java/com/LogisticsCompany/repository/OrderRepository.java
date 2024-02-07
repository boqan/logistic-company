package com.LogisticsCompany.repository;

import com.LogisticsCompany.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository interface for managing order entities in the database.
 * This interface extends the JpaRepository interface, providing CRUD (Create, Read, Update, Delete) operations
 * for order entities. It allows for easy interaction with the database to perform operations such as
 * saving, retrieving, updating, and deleting order records.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Custom methods if needed
}
