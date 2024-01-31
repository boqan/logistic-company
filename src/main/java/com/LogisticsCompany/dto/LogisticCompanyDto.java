package com.LogisticsCompany.dto;

import com.LogisticsCompany.model.IdGenerator;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogisticCompanyDto extends IdGenerator {

    private String name;

    private String country;

    private List<OfficeDto> offices;
}
