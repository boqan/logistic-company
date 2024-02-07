package com.LogisticsCompany.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Represents a request for registering a new company in the system.
 * This class contains all necessary information required for the registration process,
 * including credentials (username and password), contact information (email), and company details
 * (company name and country).
 *
 * The Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor) are used to
 * automatically generate getters, setters, a no-argument constructor, and an all-argument constructor,
 * simplifying the boilerplate code typically associated with POJOs (Plain Old Java Objects).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCompanyRequest {

    private String username;

    private String password;

    private String email;

    private String companyName;

    private String country;
}
