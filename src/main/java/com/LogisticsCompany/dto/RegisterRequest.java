package com.LogisticsCompany.dto;

import com.LogisticsCompany.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Represents a request for registering a new account in the system.
 * This class contains all the necessary information required for the registration process,
 * including the account credentials (username and password), contact information (email),
 * the role of the account within the system, and a connected ID, which may link the account
 * to another entity in the system (e.g., a specific company or office ID).
 *
 * The Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor) are utilized to
 * automatically generate getters, setters, and constructors, thereby reducing boilerplate code.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;

    private String password;

    private String email;

    private AccountType role;

    private Long connectedId;
}
