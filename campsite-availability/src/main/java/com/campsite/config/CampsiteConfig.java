package com.campsite.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class CampsiteConfig {

    public @Bean MongoClient mongoClient() {
        return new MongoClient("localhost");
    }

//    public @Bean MongoClientFactoryBean mongo() {
//        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
//        mongo.setHost("100.100.100.100");
//        return mongo;
//    }

    @Autowired
    public @Bean MongoDbFactory mongoDbFactory(MongoClient mclient) {
        return new SimpleMongoDbFactory(mclient, "campsitedb");
    }

    @Autowired
    public @Bean MongoTemplate mongoTemplate(MongoClient mclient) {
        return new MongoTemplate(mclient, "campsitedb");
    }
}