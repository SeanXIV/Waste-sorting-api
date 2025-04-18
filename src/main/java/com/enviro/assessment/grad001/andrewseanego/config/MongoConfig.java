package com.enviro.assessment.grad001.andrewseanego.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.enviro.assessment.grad001.andrewseanego.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Override
    protected String getDatabaseName() {
        // Extract database name from URI
        String dbName = "waste_management"; // Default name
        
        if (mongoUri != null && !mongoUri.isEmpty()) {
            int lastSlashIndex = mongoUri.lastIndexOf('/');
            if (lastSlashIndex != -1 && lastSlashIndex < mongoUri.length() - 1) {
                dbName = mongoUri.substring(lastSlashIndex + 1);
                // Remove query parameters if any
                int queryParamIndex = dbName.indexOf('?');
                if (queryParamIndex != -1) {
                    dbName = dbName.substring(0, queryParamIndex);
                }
            }
        }
        
        return dbName;
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoUri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }
}
