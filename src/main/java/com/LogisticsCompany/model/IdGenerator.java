package com.LogisticsCompany.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;


@MappedSuperclass
public class IdGenerator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
