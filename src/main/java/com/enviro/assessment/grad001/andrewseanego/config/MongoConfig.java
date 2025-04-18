package com.enviro.assessment.grad001.andrewseanego.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.lang.NonNull;

@Configuration
@EnableMongoRepositories(basePackages = "com.enviro.assessment.grad001.andrewseanego.repository")
@Profile("!embedded")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Override
    @NonNull
    protected String getDatabaseName() {
        // Extract database name from URI or use default
        String dbName = "waste_management"; // Default name

        if (mongoUri != null && !mongoUri.isEmpty()) {
            try {
                ConnectionString connectionString = new ConnectionString(mongoUri);
                String extractedDbName = connectionString.getDatabase();
                if (extractedDbName != null && !extractedDbName.isEmpty()) {
                    dbName = extractedDbName;
                }
            } catch (Exception e) {
                // Fallback to manual parsing if ConnectionString parsing fails
                int lastSlashIndex = mongoUri.lastIndexOf('/');
                if (lastSlashIndex != -1 && lastSlashIndex < mongoUri.length() - 1) {
                    String potentialDbName = mongoUri.substring(lastSlashIndex + 1);
                    // Remove query parameters if any
                    int queryParamIndex = potentialDbName.indexOf('?');
                    if (queryParamIndex != -1) {
                        potentialDbName = potentialDbName.substring(0, queryParamIndex);
                    }
                    if (!potentialDbName.isEmpty()) {
                        dbName = potentialDbName;
                    }
                }
            }
        }

        return dbName;
    }

    @Override
    @NonNull
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoUri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        // Use CustomMongoClientFactory to ensure database name is set
        SimpleMongoClientDatabaseFactory factory = new CustomMongoClientFactory(mongoUri);
        return new MongoTemplate(factory);
    }
}
