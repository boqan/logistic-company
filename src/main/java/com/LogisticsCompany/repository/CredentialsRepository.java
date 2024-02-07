package com.LogisticsCompany.repository;

import com.LogisticsCompany.model.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * Repository interface for managing user credentials.
 * This interface provides methods for querying user credentials by username and email.
 */
public interface CredentialsRepository extends JpaRepository<Credentials, Long> {

    Optional<Credentials> findByUsername(String username);
    Optional<Credentials> findByEmail(String email);
}
