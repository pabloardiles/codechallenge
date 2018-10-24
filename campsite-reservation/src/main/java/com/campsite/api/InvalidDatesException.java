package com.campsite.api;

public class InvalidDatesException extends RuntimeException {

    public InvalidDatesException(String message) {
        super(message);
    }
}
