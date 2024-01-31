package com.LogisticsCompany.service.implementation;


import com.LogisticsCompany.config.JwtUtil;
import com.LogisticsCompany.dto.AuthenticationRequest;
import com.LogisticsCompany.dto.AuthenticationResponce;
import com.LogisticsCompany.dto.RegisterRequest;
import com.LogisticsCompany.enums.AccountType;
import com.LogisticsCompany.error.UserNotFoundException;
import com.LogisticsCompany.repository.CredentialsRepository;
import com.LogisticsCompany.service.CredentialsService;
import com.LogisticsCompany.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;


@Service
public class CredentialsServiceImpl implements CredentialsService {

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    private boolean validatePassword(String password) {
        return pattern.matcher(password).matches();
    }
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
                    .build();

            credentialsRepository.save(user);
            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponce.builder()
                    .authenticationToken(jwtToken)
                    .build();
        }
    }

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
}
