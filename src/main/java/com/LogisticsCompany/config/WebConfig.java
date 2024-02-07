package com.LogisticsCompany.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures cross-origin resource sharing (CORS) settings for the web application.
 * This configuration allows the application to accept requests from specific origins,
 * with specified HTTP methods and headers, facilitating the integration between the frontend
 * and backend services, especially during development phases.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures CORS mappings to allow requests from specified origins and methods.
     * This method enables seamless frontend and backend integration by specifying allowed origins,
     * HTTP methods, headers, and credential settings.
     *
     * @param registry The CORS registry where configurations are applied.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}