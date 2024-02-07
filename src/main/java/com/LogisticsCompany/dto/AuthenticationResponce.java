package com.LogisticsCompany.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Represents the response sent to clients upon successful authentication.
 * This class encapsulates the authentication token that is generated after a user has been authenticated.
 * <p>
 * Annotations:
 * <ul>
 *     <li>{@code @Data} - Generates boilerplate code like getters, setters, toString, equals, and hashCode methods.</li>
 *     <li>{@code @AllArgsConstructor} - Generates a constructor with arguments for all fields.</li>
 *     <li>{@code @NoArgsConstructor} - Generates a no-argument constructor.</li>
 *     <li>{@code @Builder} - Enables the Builder pattern for creating instances of this class.</li>
 * </ul>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponce {

    private String authenticationToken;
}
