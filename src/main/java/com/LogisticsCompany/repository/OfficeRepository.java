package com.LogisticsCompany.repository;


import com.LogisticsCompany.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends JpaRepository< Office, Long> {

}
