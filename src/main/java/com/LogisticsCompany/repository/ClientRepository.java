package com.LogisticsCompany.repository;

import com.LogisticsCompany.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository interface for managing client entities in the database.
 * This interface extends the JpaRepository interface, providing CRUD (Create, Read, Update, Delete) operations
 * for client entities. It allows for easy interaction with the database to perform operations such as
 * saving, retrieving, updating, and deleting client records.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
}
