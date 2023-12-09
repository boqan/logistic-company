package com.LogisticsCompany.dto;

import com.LogisticsCompany.model.IdGenerator;
import com.LogisticsCompany.model.LogisticCompany;
import com.LogisticsCompany.model.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeDTOnoCompany extends BusinessEntityDTOnoOffice {

    private String address;

    private List<OrderDTOnoOffice> orders;


}