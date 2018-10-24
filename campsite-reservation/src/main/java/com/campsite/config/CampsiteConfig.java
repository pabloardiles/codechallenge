package com.campsite.config;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import static java.util.Collections.singletonList;

@Configuration
public class CampsiteConfig extends AbstractMongoConfiguration {

    @Value("${mongodb.host}")
    private String host;

    @Value("${mongodb.database}")
    private String database;

    @Override
    public String getDatabaseName() {
        return database;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(singletonList(new ServerAddress(host, 27017)));
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mclient) {
        MongoTemplate mongoTemplate = new MongoTemplate(mclient, database);
        mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        return mongoTemplate;
    }
    @Bean
    public MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

}
