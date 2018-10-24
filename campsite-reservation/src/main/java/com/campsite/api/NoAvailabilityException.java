package com.campsite.api;

public class NoAvailabilityException extends RuntimeException {

    public NoAvailabilityException(String message) {
        super(message);
    }
}
