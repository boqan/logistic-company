package com.LogisticsCompany.dto;

import com.LogisticsCompany.model.IdGenerator;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogisticCompanyDTOnoOffice extends IdGenerator {

    private String name;

    private String country;

    private List<OfficeDTOnoCompany> offices;
}
