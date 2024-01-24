package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.CredentialsDTO;
import com.LogisticsCompany.model.Credentials;

public interface CredentialsService {
    CredentialsDTO registerCredentials(Credentials credentials);
}
