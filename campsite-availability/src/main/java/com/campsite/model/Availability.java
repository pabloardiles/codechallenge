package com.campsite.model;

import java.time.LocalDate;

public class Availability {
    private LocalDate date;
    private int slots;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }
}
