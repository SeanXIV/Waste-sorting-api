package com.enviro.assessment.grad001.andrewseanego.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.lang.NonNull;

public class CustomMongoClientFactory extends SimpleMongoClientDatabaseFactory {

    public CustomMongoClientFactory(String connectionString) {
        super(ensureDatabaseName(connectionString));
    }

    @NonNull
    private static String ensureDatabaseName(String connectionString) {
        if (connectionString == null || connectionString.isEmpty()) {
            return "mongodb://localhost:27017/waste_management";
        }

        try {
            ConnectionString connString = new ConnectionString(connectionString);
            String database = connString.getDatabase();
            
            if (database == null || database.isEmpty()) {
                // If no database is specified, append the default database name
                if (connectionString.contains("?")) {
                    // If there are query parameters, insert the database before them
                    int queryIndex = connectionString.indexOf('?');
                    String beforeQuery = connectionString.substring(0, queryIndex);
                    String afterQuery = connectionString.substring(queryIndex);
                    
                    // Check if the URL ends with a slash before the query
                    if (beforeQuery.endsWith("/")) {
                        return beforeQuery + "waste_management" + afterQuery;
                    } else {
                        return beforeQuery + "/waste_management" + afterQuery;
                    }
                } else {
                    // No query parameters, just append the database name
                    if (connectionString.endsWith("/")) {
                        return connectionString + "waste_management";
                    } else {
                        return connectionString + "/waste_management";
                    }
                }
            }
        } catch (Exception e) {
            // If parsing fails, append the default database name
            if (connectionString.contains("?")) {
                // If there are query parameters, insert the database before them
                int queryIndex = connectionString.indexOf('?');
                String beforeQuery = connectionString.substring(0, queryIndex);
                String afterQuery = connectionString.substring(queryIndex);
                
                // Check if the URL ends with a slash before the query
                if (beforeQuery.endsWith("/")) {
                    return beforeQuery + "waste_management" + afterQuery;
                } else {
                    return beforeQuery + "/waste_management" + afterQuery;
                }
            } else {
                // No query parameters, just append the database name
                if (connectionString.endsWith("/")) {
                    return connectionString + "waste_management";
                } else {
                    return connectionString + "/waste_management";
                }
            }
        }
        
        return connectionString;
    }
}
