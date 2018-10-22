package com.campsite.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Availability {
    @Id
    public String availabilityId;
    public Date date;
    public int slots;
}
