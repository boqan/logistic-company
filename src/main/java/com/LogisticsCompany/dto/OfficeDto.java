package com.LogisticsCompany.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
/**
 * Data Transfer Object (DTO) for an office, extending {@link BusinessEntityDto} to include office-specific information.
 * This class encapsulates data for an office within a company, including its company association, location address,
 * and the list of orders managed or processed by this office.
 *
 * Annotations from the Lombok library (@Data, @NoArgsConstructor, @AllArgsConstructor) are used to
 * automatically generate getters, setters, constructors, and other common methods, minimizing boilerplate code.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeDto extends BusinessEntityDto {

    private Long companyId;

    private String address;

    private List<OrderDTOSenderReceiverWithIds> orders;


}