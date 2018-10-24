package com.campsite.validator;

import com.campsite.api.InvalidDatesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.Period;

public class DateUtils {

    private static final Logger logger = LogManager.getLogger(DateUtils.class);

    private DateUtils() {}

    public static void validateDateRange(LocalDate date1, LocalDate date2) {
        if (date1.isAfter(date2)) {
            logger.error("Invalid date range: <{}> and <{}>!", date1, date2);
            throw new InvalidDatesException("Invalid date range");
        }
        if (getDateCount(date1, date2) > 3) {
            logger.error("Reservation cannot exceed 3 days: <{}> and <{}>!", date1, date2);
            throw new InvalidDatesException("Reservation cannot exceed 3 days");
        }

        LocalDate now = LocalDate.now();
        if (LocalDate.now().isEqual(date1) || date1.isAfter(now.plusMonths(1))) {
            logger.error("Invalid allowed reservation period: <{}> and <{}>!", date1, date2);
            throw new InvalidDatesException("Invalid allowed reservation period");
        }
    }

    public static int getDateCount(LocalDate from, LocalDate to) {
        Period period = Period.between(from, to);
        return period.getYears() * 365 + period.getMonths() * 30 + period.getDays() + 1;
    }
}
