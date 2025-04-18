package com.enviro.assessment.grad001.andrewseanego.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.util.Arrays;

@Configuration
public class MongoHealthChecker {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    private final Environment environment;

    public MongoHealthChecker(Environment environment) {
        this.environment = environment;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void checkMongoConnection() {
        // Skip check if already in fallback mode
        if (Arrays.asList(environment.getActiveProfiles()).contains("fallback")) {
            return;
        }

        try {
            // Try to connect to MongoDB
            ConnectionString connectionString = new ConnectionString(mongoUri);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();
            
            try (MongoClient client = MongoClients.create(settings)) {
                // Try to access the database
                client.listDatabaseNames().first();
                System.out.println("MongoDB connection successful!");
            }
        } catch (MongoException e) {
            System.err.println("MongoDB connection failed: " + e.getMessage());
            System.err.println("Switching to fallback in-memory MongoDB mode");
            
            // Switch to fallback profile
            if (environment instanceof ConfigurableEnvironment) {
                ((ConfigurableEnvironment) environment).addActiveProfile("fallback");
            }
        }
    }
}
