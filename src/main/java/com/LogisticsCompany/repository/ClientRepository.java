package com.LogisticsCompany.repository;

import com.LogisticsCompany.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
