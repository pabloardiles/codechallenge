package com.campsite.repository;

import com.campsite.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReservationRepository extends MongoRepository<Reservation, Long> {

    List<Reservation> findByArrivalDateAndDepartureDate(String date1, String date2);
}
