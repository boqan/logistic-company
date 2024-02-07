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
/**
 * Defines the authentication and registration services for various types of users, including general users,
 * companies, employees, and clients. This interface outlines the methods required to register new accounts
 * and to authenticate existing users. It supports different registration processes for various user roles
 * and handles login authentication.
 */
public interface CredentialsService {

    /**
     * Registers a new user with the provided registration details.
     *
     * @param registerRequest The request containing the registration details.
     * @return An AuthenticationResponse containing the registration outcome.
     */
    AuthenticationResponce register(RegisterRequest registerRequest);

    /**
     * Authenticates a user based on the provided login credentials.
     *
     * @param authenticationRequest The request containing the authentication credentials.
     * @return An AuthenticationResponse containing the authentication outcome.
     * @throws UserNotFoundException if the user cannot be found with the provided credentials.
     */
    AuthenticationResponce login(AuthenticationRequest authenticationRequest) throws UserNotFoundException;

    /**
     * Registers a new company with the provided company registration details.
     *
     * @param registerCompanyRequest The request containing the company registration details.
     * @return An AuthenticationResponse containing the registration outcome.
     * @throws EntityAlreadyExistsInDbException if the company already exists in the database.
     */
    AuthenticationResponce registerCompany(RegisterCompanyRequest registerCompanyRequest) throws EntityAlreadyExistsInDbException;

    /**
     * Registers a new employee with the provided employee registration details.
     *
     * @param registerEmployeeRequest The request containing the employee registration details.
     * @return An AuthenticationResponse containing the registration outcome.
     * @throws InvalidDTOException if the provided DTO is invalid.
     * @throws EntityAlreadyExistsInDbException if the employee already exists in the database.
     */
    AuthenticationResponce registerEmployee(RegisterEmployeeRequest registerEmployeeRequest) throws InvalidDTOException, EntityAlreadyExistsInDbException;

    /**
     * Registers a new client with the provided client registration details.
     *
     * @param registerClientRequest The request containing the client registration details.
     * @return An AuthenticationResponse containing the registration outcome.
     * @throws InvalidDTOException if the provided DTO is invalid.
     * @throws EntityAlreadyExistsInDbException if the client already exists in the database.
     */
    AuthenticationResponce registerClient(RegisterClientRequest registerClientRequest) throws InvalidDTOException, EntityAlreadyExistsInDbException;
}