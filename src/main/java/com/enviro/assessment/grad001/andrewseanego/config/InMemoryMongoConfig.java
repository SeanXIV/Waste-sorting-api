package com.enviro.assessment.grad001.andrewseanego.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;

@Configuration
@EnableMongoRepositories(basePackages = "com.enviro.assessment.grad001.andrewseanego.repository")
@Profile("fallback")
public class InMemoryMongoConfig {

    private static final String DATABASE_NAME = "waste_management";

    @Bean
    @Primary
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017/" + DATABASE_NAME);
    }

    @Bean
    @Primary
    public MongoTemplate mongoTemplate() throws IOException {
        return new MongoTemplate(mongoClient(), DATABASE_NAME);
    }
}
