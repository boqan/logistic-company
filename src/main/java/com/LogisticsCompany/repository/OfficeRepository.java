package com.LogisticsCompany.repository;


import com.LogisticsCompany.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfficeRepository extends JpaRepository< Office, Long> {

    Optional<Office> findFirstByOrderByIdAsc();
}
