package com.campsite.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class DateUtils {

    private static final Logger logger = LogManager.getLogger(DateUtils.class);
    private static final int DEFAULT_SEARCH_PERIOD = 1;

    private DateUtils() {}

    public static DateRange getDateRange(String date1, String date2) {
        LocalDate d1 = LocalDate.parse(date1);
        LocalDate d2;
        if (date2 == null) {
            d2 = d1.plusMonths(DEFAULT_SEARCH_PERIOD);
        } else {
            d2 = LocalDate.parse(date2);
        }
        if (d1.isBefore(LocalDate.now())) {
            final String msg = "Time travelling is not supported in this version: " + d1;
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (d1.isAfter(d2)) {
            final String msg = "Invalid parameters " + date1 + " and " + date2;
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        return new DateRange(d1, d2);
    }

    public static class DateRange {
        public final LocalDate from;
        public final LocalDate to;
        public DateRange(LocalDate d1, LocalDate d2) {
            this.from = d1;
            this.to = d2;
        }
    }
}
