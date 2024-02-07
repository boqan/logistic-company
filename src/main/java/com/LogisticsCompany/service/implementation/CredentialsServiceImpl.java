package com.LogisticsCompany.service.implementation;


import com.LogisticsCompany.config.JwtUtil;
import com.LogisticsCompany.dto.*;
import com.LogisticsCompany.enums.AccountType;
import com.LogisticsCompany.error.EntityAlreadyExistsInDbException;
import com.LogisticsCompany.error.InvalidDTOException;
import com.LogisticsCompany.error.UserNotFoundException;
import com.LogisticsCompany.model.Employee;
import com.LogisticsCompany.model.LogisticCompany;
import com.LogisticsCompany.repository.CredentialsRepository;
import com.LogisticsCompany.repository.LogisticCompanyRepository;
import com.LogisticsCompany.service.ClientService;
import com.LogisticsCompany.service.CredentialsService;
import com.LogisticsCompany.model.Credentials;
import com.LogisticsCompany.service.EmployeeService;
import com.LogisticsCompany.service.LogisticCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Service implementation for managing user credentials.
 * This service provides methods for user registration, login, and other related operations.
 */
@Service
public class CredentialsServiceImpl implements CredentialsService {

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private LogisticCompanyService logisticCompanyService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private LogisticCompanyRepository logisticCompanyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
    /**
     * Validates a password against a predefined pattern.
     *
     * @param password The password to validate.
     * @return True if the password meets the strength requirements, false otherwise.
     */
    private boolean validatePassword(String password) {
        return pattern.matcher(password).matches();
    }
    /**
     * Registers a new user based on the provided registration request.
     * Validates the password strength before registration.
     *
     * @param registerRequest The registration request containing user details.
     * @return An authentication response with a JWT token upon successful registration.
     * @throws IllegalArgumentException If the password does not meet the strength requirements.
     */
    @Override
    public AuthenticationResponce register(RegisterRequest registerRequest) {
        if (!validatePassword(registerRequest.getPassword())) {
            throw new IllegalArgumentException("Password does not meet the strength requirements.");
        }
        else{
            Credentials user = Credentials.builder()
                    .username(registerRequest.getUsername())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .accountType(registerRequest.getRole())
                    .connectedId(registerRequest.getConnectedId())
                    .build();

            credentialsRepository.save(user);
            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponce.builder()
                    .authenticationToken(jwtToken)
                    .build();
        }
    }
    /**
     * Logs in a user using the provided authentication request.
     *
     * @param authenticationRequest The authentication request containing the username and password.
     * @return An authentication response with a JWT token upon successful login.
     * @throws UserNotFoundException If the user is not found in the database.
     */
    @Override
    public AuthenticationResponce login(AuthenticationRequest authenticationRequest) throws UserNotFoundException {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
            System.out.println("Authentication Successful");
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw e;
        }

        Credentials user = credentialsRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponce.builder()
                .authenticationToken(jwtToken)
                .build();

    }

    // When registering a company we check if the company exist already if id does we throw an exception
    // If the company does not exist we create it and then we create the user
    // if the user already exist we throw an exception
    // if both of them are valid and not created we have them and return the token
    /**
     * Registers a new company and a user associated with it. Checks if the company and user already exist,
     * and throws exceptions accordingly. Returns an authentication response with a JWT token upon successful registration.
     *
     * @param registerCompanyRequest The registration request for a company and user.
     * @return An authentication response with a JWT token upon successful registration.
     * @throws EntityAlreadyExistsInDbException If the company or user already exists in the database.
     */
    @Override
    public AuthenticationResponce registerCompany(RegisterCompanyRequest registerCompanyRequest) throws EntityAlreadyExistsInDbException {
        // Check if the company exists
        LogisticCompany logisticCompany_ = logisticCompanyRepository.findByName(registerCompanyRequest.getCompanyName()).orElse(null);
        if (logisticCompany_ != null) {
            throw new EntityAlreadyExistsInDbException("Company already exists");
        }
        else {
            // Create the company
            LogisticCompany logisticCompany = LogisticCompany.builder()
                    .name(registerCompanyRequest.getCompanyName())
                    .country(registerCompanyRequest.getCountry())
                    .build();

            LogisticCompanyDto logisticCompanyDto = logisticCompanyService.saveLogisticCompany(logisticCompany);
            // create the user
            Credentials user = Credentials.builder()
                    .username(registerCompanyRequest.getUsername())
                    .email(registerCompanyRequest.getEmail())
                    .password(passwordEncoder.encode(registerCompanyRequest.getPassword()))
                    .accountType(AccountType.COMPANY_MANAGER)
                    .connectedId(logisticCompanyDto.getId())
                    .build();


            credentialsRepository.save(user);

            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponce.builder()
                    .authenticationToken(jwtToken)
                    .build();
        }
    }
    /**
     * Registers a new employee and a user associated with it. Checks if the user already exists,
     * and throws an exception accordingly. Returns an authentication response with a JWT token upon successful registration.
     *
     * @param registerEmployeeRequest The registration request for an employee and user.
     * @return An authentication response with a JWT token upon successful registration.
     * @throws InvalidDTOException If the employee DTO is invalid.
     * @throws EntityAlreadyExistsInDbException If the user already exists in the database.
     */
    @Override
    public AuthenticationResponce registerEmployee(RegisterEmployeeRequest registerEmployeeRequest) throws InvalidDTOException, EntityAlreadyExistsInDbException {

        if(!credentialsRepository.findByUsername(registerEmployeeRequest.getUsername()).isPresent()
            || !credentialsRepository.findByEmail(registerEmployeeRequest.getEmail()).isPresent()) {

            // Create the company
            EmployeeDTO employee = EmployeeDTO.builder()
                    .name(registerEmployeeRequest.getName())
                    .salary(registerEmployeeRequest.getSalary())
                    .employeeType(registerEmployeeRequest.getEmployeeType())
                    .officeID(registerEmployeeRequest.getOfficeID())
                    .build();


            EmployeeDTO employeeDTO = employeeService.saveEmployee(employee);
            System.out.println(employeeDTO.getId());
            // create the user
            Credentials user = Credentials.builder()
                    .username(registerEmployeeRequest.getUsername())
                    .email(registerEmployeeRequest.getEmail())
                    .password(passwordEncoder.encode(registerEmployeeRequest.getPassword()))
                    .accountType(AccountType.EMPLOYEE)
                    .connectedId(employeeDTO.getId())
                    .build();


            credentialsRepository.save(user);

            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponce.builder()
                    .authenticationToken(jwtToken)
                    .build();
        }
        else throw new EntityAlreadyExistsInDbException("Credentials already exists");
    }
    /**
     * Registers a new client and a user associated with it. Checks if the user already exists,
     * and throws an exception accordingly. Returns an authentication response with a JWT token upon successful registration.
     *
     * @param registerClientRequest The registration request for a client and user.
     * @return An authentication response with a JWT token upon successful registration.
     * @throws InvalidDTOException If the client DTO is invalid.
     * @throws EntityAlreadyExistsInDbException If the user already exists in the database.
     */
    @Override
    public AuthenticationResponce registerClient(RegisterClientRequest registerClientRequest) throws InvalidDTOException, EntityAlreadyExistsInDbException {

        if(!credentialsRepository.findByUsername(registerClientRequest.getUsername()).isPresent()
                || !credentialsRepository.findByEmail(registerClientRequest.getEmail()).isPresent()) {

            ClientDTO client = ClientDTO.builder()
                    .name(registerClientRequest.getName())
                    .officeId(registerClientRequest.getOfficeID())
                    .build();

            ClientDTO clientDTO = clientService.createClient(client);

            Credentials user = Credentials.builder()
                    .username(registerClientRequest.getUsername())
                    .email(registerClientRequest.getEmail())
                    .password(passwordEncoder.encode(registerClientRequest.getPassword()))
                    .accountType(AccountType.CLIENT)
                    .connectedId(clientDTO.getId())
                    .build();

            credentialsRepository.save(user);

            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponce.builder()
                    .authenticationToken(jwtToken)
                    .build();
        }
        else throw new EntityAlreadyExistsInDbException("Credentials already exists");

    }
}
