package com.LogisticsCompany.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@MappedSuperclass
@Getter
@Setter
public class IdGenerator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
