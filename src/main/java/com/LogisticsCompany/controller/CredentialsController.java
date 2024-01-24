package com.LogisticsCompany.controller;

import com.LogisticsCompany.dto.CredentialsDTO;
import com.LogisticsCompany.dto.LoginDTO;
import com.LogisticsCompany.model.Credentials;
import com.LogisticsCompany.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CredentialsController {

    @Autowired
    private CredentialsService credentialsService;

    @PostMapping("/register")
    public CredentialsDTO register(@RequestBody Credentials credentials) {
        return credentialsService.registerCredentials(credentials);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        return credentialsService.loginCredentials(loginDTO);
    }

}
