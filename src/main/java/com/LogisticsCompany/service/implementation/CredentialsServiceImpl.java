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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


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



    @Override
    public AuthenticationResponce register(RegisterRequest registerRequest) {
        Credentials user = Credentials.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .accountType(AccountType.CLIENT)
                .build();
        credentialsRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponce.builder()
                .authenticationToken(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponce login(AuthenticationRequest authenticationRequest) throws UserNotFoundException {
        ////////////////////////////////////////////////////
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        ////////////////////////////////////////////////////

        Credentials user = credentialsRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponce.builder()
                .authenticationToken(jwtToken)
                .build();
    }
}
