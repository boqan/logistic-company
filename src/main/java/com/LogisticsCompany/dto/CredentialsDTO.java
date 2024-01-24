package com.LogisticsCompany.dto;

import com.LogisticsCompany.enums.AccountType;
import com.LogisticsCompany.model.IdGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDTO extends IdGenerator {

    private String username;

    private String password;

    private String email;

    //private AccountType accountType;

}