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

@Service
public class CredentialsServiceImpl implements CredentialsService {

    @Autowired
    private CredentialsRepository credentialsRepository;


    @Override
    public CredentialsDTO registerCredentials(Credentials credentials) {
        // Check if the username is already taken
        if (credentialsRepository.findByUsername(credentials.getUsername()) != null) {
            return null;
        }

        return EntityMapper.mapToDTO(credentialsRepository.save(credentials));

    }

    @Override
    public ResponseEntity<String> loginCredentials(LoginDTO login) {
        Credentials credentials = credentialsRepository.findByUsername(login.getUsername());
        if (credentials != null && credentials.getPassword().equals(login.getPassword())) {
            return ResponseEntity.ok("User logged in successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");

    }
}
