package com.LogisticsCompany.dto;

import com.LogisticsCompany.model.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessEntityDTOnoOffice extends IdGenerator {
    private BigDecimal revenue = BigDecimal.ZERO;

    private List<EmployeeDTOnoOffice> employees;

    private List<ClientDTO> clients;
}