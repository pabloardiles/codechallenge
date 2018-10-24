package com.campsite.api;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(String reservationId) {
        super(reservationId);
    }
}
