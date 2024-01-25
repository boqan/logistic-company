package com.LogisticsCompany.service.implementation;


import com.LogisticsCompany.dto.CredentialsDTO;
import com.LogisticsCompany.dto.LoginDTO;
import com.LogisticsCompany.mapper.EntityMapper;
import com.LogisticsCompany.model.Credentials;
import com.LogisticsCompany.repository.CredentialsRepository;
import com.LogisticsCompany.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


@Service
public class CredentialsServiceImpl implements CredentialsService {

    @Autowired
    private CredentialsRepository credentialsRepository;


    String hashWith256(String textToHash) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] byteOfTextToHash = textToHash.getBytes(StandardCharsets.UTF_8);
            byte[] hashedByetArray = digest.digest(byteOfTextToHash);
            String encoded = Base64.getEncoder().encodeToString(hashedByetArray);
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public  ResponseEntity<String> registerCredentials(Credentials credentials) {
        // Check if the username is already taken
        if (credentialsRepository.findByUsername(credentials.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Already existing username");
        }
        else if(credentialsRepository.findByEmail(credentials.getEmail()) != null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Already existing email");
        }

        String hashedPassword = hashWith256(credentials.getPassword());
        credentials.setPassword(hashedPassword);

        EntityMapper.mapToDTO(credentialsRepository.save(credentials));
        return ResponseEntity.ok("User signed up successfully");
    }

    @Override
    public ResponseEntity<String> loginCredentials(LoginDTO login) {
        Credentials credentials = credentialsRepository.findByUsername(login.getUsername());
        String hashedPassword = hashWith256(login.getPassword());
        if (credentials != null && credentials.getPassword().equals(hashedPassword)) {
            //String token = jwtUtil.generateToken(credentials.getUsername());
            return ResponseEntity.ok("User logged in successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong username or password");

    }
}
