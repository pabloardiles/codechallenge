package com.campsite.model;

import org.springframework.data.annotation.Id;

public class Reservation {

    @Id
    public String reservationId;
    public String arrivalDate;
    public String departureDate;
}
