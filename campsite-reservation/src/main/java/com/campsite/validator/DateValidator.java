package com.campsite.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class DateValidator {

    private static final Logger logger = LogManager.getLogger(DateValidator.class);

    private DateValidator() {}

    public static void validateDateRange(String date1, String date2) {
        LocalDate d1 = LocalDate.parse(date1);
        LocalDate d2 = LocalDate.parse(date2);
        if (d1.isAfter(d2)) {
            logger.error("Invalid parameters <{}> and <{}>!", date1, date2);
            throw new IllegalArgumentException();
        }
    }
}
