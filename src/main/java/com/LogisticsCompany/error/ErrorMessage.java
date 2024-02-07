package com.LogisticsCompany.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
/**
 * Represents an error message structure for API responses. This class is used to provide
 * standardized error information to clients, including the HTTP status code and a descriptive
 * error message.
 * <p>
 * Annotations:
 * <ul>
 *     <li>{@code @Data} - Lombok annotation to automatically generate getters, setters,
 *     {@code toString()}, {@code equals()}, and {@code hashCode()} methods.</li>
 *     <li>{@code @AllArgsConstructor} - Generates a constructor with arguments for every field in the class.</li>
 *     <li>{@code @NoArgsConstructor} - Generates a no-argument constructor, essential for serialization/deserialization
 *     and other frameworks that require a default constructor.</li>
 * </ul>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    private HttpStatus status;
    private String message;
}
