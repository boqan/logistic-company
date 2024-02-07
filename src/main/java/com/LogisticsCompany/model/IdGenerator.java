package com.LogisticsCompany.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
/**
 * A base class for entities providing an auto-generated identifier.
 */
@Data
@MappedSuperclass
public class IdGenerator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
