package com.LogisticsCompany.dto;



import com.LogisticsCompany.model.Office;

import lombok.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogisticCompanyDTOnoCompany extends BusinessEntityDTOnoCompany {

    private String name;

    private String country;

    private List<OfficeDTOnoCompany> offices;
}
