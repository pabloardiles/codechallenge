package com.campsite.repository;

import com.campsite.model.Availability;
import com.campsite.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public class AvailabilityRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public AvailabilityRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Availability> findAvailability(LocalDate from, LocalDate to) {
        Query query = Query.query(Criteria.where("date").gte(from)
                .andOperator(Criteria.where("date").lte(to)));
        return mongoTemplate.find(query, Availability.class);
    }
}
