package com.LogisticsCompany.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an authentication request carrying credentials for user authentication.
 * This class is typically used to transport user login information, such as username and password,
 * from the client to the server for authentication purposes.
 *
 * Annotations from the Lombok library (@Data, @AllArgsConstructor, @NoArgsConstructor, @Builder) are used to
 * automatically generate boilerplate code such as getters, setters, constructors, and a builder pattern implementation.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {

    private String username;

    private String password;
}
