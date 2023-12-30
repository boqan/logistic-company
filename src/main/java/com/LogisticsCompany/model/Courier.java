package com.LogisticsCompany.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Courier extends Employee{
    @NotBlank(message = "Courier should have a delivery area")
    @Column(name = "delivery_area", nullable = false, length = 255)
    private String deliveryArea;

    @NotBlank(message = "Courier should have a vehicle ID")
    @Column(name = "vehicle_id", nullable = false, length = 255)
    private String vehicleID;

}
