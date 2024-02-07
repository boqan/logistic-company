package com.LogisticsCompany.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Data Transfer Object (DTO) for user login credentials.
 * This class is used to transfer login information, such as username and password, from clients to the server.
 * It serves as a simple data container without business logic, primarily used for authentication purposes.
 *
 * The Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor) are utilized here to automatically
 * generate boilerplate code, including getters, setters, a no-argument constructor, and an all-argument constructor.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private String username;
    private String password;
}
