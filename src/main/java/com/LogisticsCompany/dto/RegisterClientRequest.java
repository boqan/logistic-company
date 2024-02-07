package com.LogisticsCompany.dto;

import com.LogisticsCompany.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Represents a request to register a new client within the logistics system. This class encapsulates
 * all necessary information needed to create a client account, including personal details, account credentials,
 * and the office they are associated with.
 * <p>
 * Annotations:
 * <ul>
 *     <li>{@code @Data} - Lombok annotation to generate boilerplate code such as getters, setters,
 *     {@code toString()}, {@code equals()}, and {@code hashCode()} methods automatically.</li>
 *     <li>{@code @AllArgsConstructor} - Lombok annotation to generate a constructor with one parameter for each field.</li>
 *     <li>{@code @NoArgsConstructor} - Lombok annotation to generate a no-argument constructor, required for serialization
 *     and other frameworks.</li>
 * </ul>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterClientRequest {

    private String name;

    private Long officeID;

    private String username;

    private String password;

    private String email;

    private AccountType role;

    private Long connectedId;
}
