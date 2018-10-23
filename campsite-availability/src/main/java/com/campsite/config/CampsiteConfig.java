package com.campsite.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ConfigurationProperties
public class CampsiteConfig {

    @Value("${mongodb.host}")
    private String host;

    @Value("${mongodb.database}")
    private String database;

    public @Bean MongoClient mongoClient() {
        return new MongoClient(host);
    }

    @Autowired
    public @Bean MongoTemplate mongoTemplate(MongoClient mclient) {
        return new MongoTemplate(mclient, database);
    }
}
