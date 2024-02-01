package com.LogisticsCompany.controller;

import com.LogisticsCompany.dto.AuthenticationRequest;
import com.LogisticsCompany.dto.AuthenticationResponce;
import com.LogisticsCompany.dto.RegisterCompanyRequest;
import com.LogisticsCompany.dto.RegisterRequest;
import com.LogisticsCompany.error.UserNotFoundException;
import com.LogisticsCompany.model.LogisticCompany;
import com.LogisticsCompany.service.CredentialsService;
import com.LogisticsCompany.service.LogisticCompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class CredentialsController {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private LogisticCompanyService logisticCompanyService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponce> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(credentialsService.register(registerRequest));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponce> login(@RequestBody AuthenticationRequest authenticationRequest) throws UserNotFoundException {
        return ResponseEntity.ok(credentialsService.login(authenticationRequest));
    }

    @PostMapping("/register_company")
    public ResponseEntity<AuthenticationResponce> saveLogisticCompany(@RequestBody RegisterCompanyRequest registerCompanyRequest) {
        return ResponseEntity.ok(credentialsService.registerCompany(registerCompanyRequest));
    }

}
