package com.LogisticsCompany.dto;

import com.LogisticsCompany.enums.AccountType;
import com.LogisticsCompany.model.IdGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Data Transfer Object (DTO) for user credentials.
 * Extends {@link IdGenerator} to inherit ID generation capabilities.
 * This class encapsulates the essential authentication information of a user, including username, password, and email.
 *
 * Annotations from the Lombok library (@Data, @NoArgsConstructor, @AllArgsConstructor) are used to
 * automatically generate getters, setters, and constructors for all fields.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDTO extends IdGenerator {

    private String username;

    private String password;

    private String email;

    //private AccountType accountType;

}