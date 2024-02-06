package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.*;
import com.LogisticsCompany.error.EntityAlreadyExistsInDbException;
import com.LogisticsCompany.error.InvalidDTOException;
import com.LogisticsCompany.error.UserNotFoundException;
import com.LogisticsCompany.model.Credentials;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface CredentialsService {


    AuthenticationResponce register(RegisterRequest registerRequest);

    AuthenticationResponce login(AuthenticationRequest authenticationRequest) throws UserNotFoundException;

    AuthenticationResponce registerCompany(RegisterCompanyRequest registerCompanyRequest) throws EntityAlreadyExistsInDbException;

    AuthenticationResponce registerEmployee(RegisterEmployeeRequest registerEmployeeRequest) throws InvalidDTOException, EntityAlreadyExistsInDbException;

    AuthenticationResponce registerClient(RegisterClientRequest registerClientRequest) throws InvalidDTOException, EntityAlreadyExistsInDbException;
}
