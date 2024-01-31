package com.LogisticsCompany.dto;

import com.LogisticsCompany.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;

    private String password;

    private String email;

    private AccountType role;

    private Long connectedId;
}
