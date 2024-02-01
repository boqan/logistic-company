package com.LogisticsCompany.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
