package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.CredentialsDTO;
import com.LogisticsCompany.dto.LoginDTO;
import com.LogisticsCompany.model.Credentials;
import org.springframework.http.ResponseEntity;

public interface CredentialsService {
    ResponseEntity<String> registerCredentials(Credentials credentials);

    ResponseEntity<String> loginCredentials(LoginDTO loginDTO);
}
