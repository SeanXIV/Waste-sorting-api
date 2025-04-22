package com.enviro.assessment.grad001.andrewseanego.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class CorsConfig {

    @Value("${cors.allowed-origins:*}")
    private String allowedOrigins;

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        // Allow all origins
        config.setAllowedOriginPatterns(Collections.singletonList("*"));

        // Allow all common HTTP methods
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // Allow all headers
        config.setAllowedHeaders(Arrays.asList("*"));

        // Allow credentials
        config.setAllowCredentials(true);

        // Expose headers
        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));

        // How long the response from a pre-flight request can be cached
        config.setMaxAge(3600L);

        // Apply this configuration to all paths
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
