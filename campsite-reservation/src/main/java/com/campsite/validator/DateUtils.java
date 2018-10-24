package com.campsite.validator;

import com.campsite.api.InvalidDatesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.Period;

public class DateUtils {

    private static final Logger logger = LogManager.getLogger(DateUtils.class);
    private static final int MAX_RESERVATION_DAYS = 3;
    private static final int ALLOWED_RESERVATION_PERIOD_MONTHS = 1;

    private DateUtils() {}

    public static void validateDateRange(LocalDate date1, LocalDate date2) {
        if (date1.isAfter(date2)) {
            logger.error("Invalid date range: <{}> and <{}>!", date1, date2);
            throw new InvalidDatesException("Invalid date range");
        }
        if (getDaysCount(date1, date2) > MAX_RESERVATION_DAYS) {
            logger.error("Reservation cannot exceed 3 days: <{}> and <{}>!", date1, date2);
            throw new InvalidDatesException("Reservation cannot exceed 3 days");
        }

        LocalDate now = LocalDate.now();
        if (LocalDate.now().isEqual(date1) || date1.isAfter(now.plusMonths(ALLOWED_RESERVATION_PERIOD_MONTHS))) {
            logger.error("Invalid allowed reservation period: <{}> and <{}>!", date1, date2);
            throw new InvalidDatesException("Invalid allowed reservation period");
        }
    }

    public static int getDaysCount(LocalDate from, LocalDate to) {
        Period period = Period.between(from, to);
        return period.getYears() * 365 + period.getMonths() * 30 + period.getDays() + 1;
    }
}
