package com.LogisticsCompany.config;

import com.LogisticsCompany.repository.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * Configures the application's security settings, including user authentication and password encoding.
 * Utilizes Spring Security for securing the application by defining beans for user details service,
 * password encoder, authentication provider, and authentication manager.
 */
@Configuration
@RequiredArgsConstructor
public class AppConfiguration {

    private final CredentialsRepository credentialsRepository;

    /**
     * Defines the UserDetailsService bean that is used to retrieve user details for authentication.
     *
     * @return UserDetailsService that loads user-specific data.
     * @throws UsernameNotFoundException if the username is not found in the credentials repository.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> credentialsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Defines the PasswordEncoder bean that is used for hashing and verifying passwords.
     *
     * @return PasswordEncoder that uses BCrypt hashing for securing passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines the AuthenticationProvider bean that integrates with Spring Security using a DAO-based approach.
     *
     * @return AuthenticationProvider that uses the DaoAuthenticationProvider for authentication.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }
    /**
     * Defines the AuthenticationManager bean that is used to process authentication requests.
     *
     * @param config The authentication configuration provided by Spring Security.
     * @return AuthenticationManager to manage authentication processes.
     * @throws Exception if an error occurs while configuring the authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
