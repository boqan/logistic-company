package com.LogisticsCompany.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationProvider;
/**
 * SecurityConfiguration configures the web security for the application.
 * It defines the security filter chain that applies the JWT authentication filter
 * before the UsernamePasswordAuthenticationFilter to support JWT based authentication.
 * This configuration is designed to disable CSRF protection, permit all requests to the auth API,
 * enforce authentication on all other requests, provide stateless session management, and
 * integrate a custom AuthenticationProvider.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    /**
     * Constructs a SecurityConfiguration with the specified JWT authentication filter and authentication provider.
     *
     * @param jwtAuthFilter the JWT authentication filter to be applied
     * @param authenticationProvider the authentication provider to handle authentication processes
     */
    public SecurityConfiguration(JwtAuthFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }
    /**
     * Configures and returns a {@link SecurityFilterChain} that specifies the security filter chain
     * for the application. It disables CSRF, configures session management to be stateless, permits all requests
     * to the auth API, enforces authentication on all other requests, and integrates the custom authentication provider.
     *
     * @param http the {@link HttpSecurity} to configure
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during the configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("api/v1/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}