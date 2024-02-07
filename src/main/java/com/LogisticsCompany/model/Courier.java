package com.LogisticsCompany.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * Represents a courier employee with a specified delivery area and vehicle ID.
 * This class extends the Employee class and adds courier-specific attributes.
 */
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

