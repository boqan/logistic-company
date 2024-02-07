package com.LogisticsCompany.repository;

import com.LogisticsCompany.model.LogisticCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Repository interface for managing logistic company entities in the database.
 * This interface extends the JpaRepository interface, providing CRUD (Create, Read, Update, Delete) operations
 * for logistic company entities. It allows for easy interaction with the database to perform operations such as
 * saving, retrieving, updating, and deleting logistic company records.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
@Repository
public interface LogisticCompanyRepository extends JpaRepository<LogisticCompany, Long> {


    Optional<LogisticCompany> findByName(String companyName);
}
