package com.LogisticsCompany.repository;


import com.LogisticsCompany.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {


    Optional<Office> findFirstByOrderByIdAsc();
    // ako ni trqbvat offices with orders
    @Query("SELECT o FROM Office o JOIN FETCH o.orders")
    List<Office> findAllOfficesWithOrders();
}
