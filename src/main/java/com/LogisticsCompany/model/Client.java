package com.LogisticsCompany.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client extends IdGenerator {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logistic_company_id")
    private LogisticCompany logisticCompany;
}
