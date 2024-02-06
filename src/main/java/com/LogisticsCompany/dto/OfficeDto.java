package com.LogisticsCompany.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeDto extends BusinessEntityDto {

    private Long companyId;

    private String address;

    private List<OrderDTOSenderReceiverWithIds> orders;


}