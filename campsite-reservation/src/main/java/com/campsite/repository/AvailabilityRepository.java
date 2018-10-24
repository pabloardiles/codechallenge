package com.campsite.repository;

import com.campsite.api.NoAvailabilityException;
import com.campsite.model.Availability;
import com.campsite.validator.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    public void decrementSlots(LocalDate date) {
        Availability av = this.getAvailability(date);
        if (av.getSlots() == 0) {
            throw new NoAvailabilityException("No slots left for date " + av.getDate());
        }
        av.setSlots(av.getSlots() - 1);
        mongoTemplate.save(av);
    }

    public void incrementSlots(LocalDate date) {
        Availability av = this.getAvailability(date);
        av.setSlots(av.getSlots() + 1);
        mongoTemplate.save(av);
    }

    private Availability getAvailability(LocalDate date) {
        Query query = new Query(Criteria.where("date").is(date));
        return mongoTemplate.findOne(query, Availability.class);
    }
}
