package com.LogisticsCompany.repository;

import com.LogisticsCompany.model.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialsRepository extends JpaRepository<Credentials, Long> {

    Optional<Credentials> findByUsername(String username);
    Optional<Credentials> findByEmail(String email);
}
