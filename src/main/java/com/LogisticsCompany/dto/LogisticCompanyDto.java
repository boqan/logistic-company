package com.LogisticsCompany.dto;

import com.LogisticsCompany.model.IdGenerator;
import lombok.*;
import java.util.List;
/**
 * Data Transfer Object (DTO) representing a logistic company. This class is used to carry data about a logistic company,
 * including its name, country, and associated offices, between different layers of the application.
 * Inherits a unique identifier from {@code IdGenerator}, which provides a unique ID for each logistic company instance.
 * <p>
 * Annotations:
 * <ul>
 *     <li>{@code @Data} - Generates boilerplate code such as getters, setters, {@code toString()}, {@code equals()},
 *     and {@code hashCode()} methods automatically.</li>
 *     <li>{@code @NoArgsConstructor} - Generates a no-argument constructor, required for serialization and deserialization processes.</li>
 *     <li>{@code @AllArgsConstructor} - Generates a constructor with parameters for all fields in the class, facilitating easy instantiation.</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogisticCompanyDto extends IdGenerator {

    private String name;

    private String country;

    private List<OfficeDto> offices;
}
