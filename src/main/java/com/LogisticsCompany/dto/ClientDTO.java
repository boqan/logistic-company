package com.LogisticsCompany.dto;

import com.LogisticsCompany.model.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Data Transfer Object (DTO) for clients. It is used to transport client data between processes,
 * specifically for transferring client-related information to and from the client layer and the server.
 * Inherits an ID from {@code IdGenerator}, which provides a unique identifier for each client instance.
 * <p>
 * Annotations used:
 * <ul>
 *     <li>{@code @Data} - Generates boilerplate code such as getters, setters, {@code toString()}, {@code equals()}, and {@code hashCode()} methods.</li>
 *     <li>{@code @Builder} - Implements the Builder pattern to allow for easier construction of {@code ClientDTO} objects.</li>
 *     <li>{@code @AllArgsConstructor} - Generates a constructor with arguments for all fields in the class.</li>
 *     <li>{@code @NoArgsConstructor} - Generates a no-argument constructor for use in serialization and other situations where a no-argument constructor is required.</li>
 * </ul>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO extends IdGenerator {

        private String name;

        private Long officeId;


}
