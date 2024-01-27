package com.LogisticsCompany.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class Courier extends Employee{

    @Column(name = "delivery_area", nullable = false, length = 255)
    private String deliveryArea;

    @Column(name = "vehicle_id", nullable = false, length = 255)
    private String vehicleID;
}

