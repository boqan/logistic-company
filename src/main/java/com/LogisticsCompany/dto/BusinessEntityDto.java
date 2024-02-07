package com.LogisticsCompany.dto;

import com.LogisticsCompany.model.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

/**
 * Data Transfer Object (DTO) for a business entity.
 * Extends {@link IdGenerator} to inherit ID generation capabilities.
 * This class encapsulates information about the business's revenue,
 * along with lists of its employees and clients.
 *
 * Annotations from Lombok (@Data, @NoArgsConstructor, @AllArgsConstructor) are used to
 * automatically generate getters, setters, and constructors for all fields.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessEntityDto extends IdGenerator {
    private BigDecimal revenue = BigDecimal.ZERO;

    private List<EmployeeDTO> employees;

    private List<ClientDTO> clients;
}