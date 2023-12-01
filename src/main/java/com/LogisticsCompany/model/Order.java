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
@Table(name = "my_order") // The name "order" is a reserved keyword in SQL that is why we need to change it
public class Order extends IdGenerator{

    @ManyToOne(fetch = FetchType.LAZY)
    private Office office;

}
