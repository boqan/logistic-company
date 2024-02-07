package com.LogisticsCompany.controller;

import com.LogisticsCompany.dto.AuthenticationRequest;
import com.LogisticsCompany.dto.AuthenticationResponce;
import com.LogisticsCompany.dto.RegisterCompanyRequest;
import com.LogisticsCompany.dto.RegisterRequest;
import com.LogisticsCompany.error.EntityAlreadyExistsInDbException;
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
/**
 * Handles authentication and registration requests for users and logistic companies.
 * This controller provides endpoints for registering new users, authenticating existing users,
 * and registering new logistic companies.
 */
@RestController
@RequestMapping("api/v1/auth")
public class CredentialsController {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private LogisticCompanyService logisticCompanyService;
    /**
     * Registers a new user in the system.
     *
     * @param registerRequest the registration request containing the user's information
     * @return a {@link ResponseEntity} containing the authentication response with the registration details
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponce> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(credentialsService.register(registerRequest));

    }
    /**
     * Authenticates a user and generates an authentication token.
     *
     * @param authenticationRequest the authentication request containing the user's credentials
     * @return a {@link ResponseEntity} containing the authentication response with the token details
     * @throws UserNotFoundException if the user cannot be found with the provided credentials
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponce> login(@RequestBody AuthenticationRequest authenticationRequest) throws UserNotFoundException {
        return ResponseEntity.ok(credentialsService.login(authenticationRequest));
    }
    /**
     * Registers a new logistic company in the system.
     *
     * @param registerCompanyRequest the registration request containing the company's information
     * @return a {@link ResponseEntity} containing the authentication response with the registration details
     * @throws EntityAlreadyExistsInDbException if the company already exists in the database
     */
    @PostMapping("/register_company")
    public ResponseEntity<AuthenticationResponce> saveLogisticCompany(@RequestBody RegisterCompanyRequest registerCompanyRequest) throws EntityAlreadyExistsInDbException {
        return ResponseEntity.ok(credentialsService.registerCompany(registerCompanyRequest));
    }

}
