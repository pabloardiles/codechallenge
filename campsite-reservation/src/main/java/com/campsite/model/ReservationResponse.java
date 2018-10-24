package com.campsite.model;

public class ReservationResponse {

    private String reservationId;

    public ReservationResponse(String id) {
        reservationId = id;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

}
