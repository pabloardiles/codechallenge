package com.campsite.api;

import com.campsite.model.Availability;
import com.campsite.model.AvailabilityResponse;
import com.campsite.model.CampsiteResponse;
import com.campsite.model.Reservation;
import com.campsite.repository.AvailabilityRepository;
import com.campsite.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CsAvailabilityController {


    //LOGS
    @Autowired
    private ReservationRepository reservationRepository; //remove this

    @Autowired
    private AvailabilityRepository availabilityRepository;

    private final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @RequestMapping(value = "/available", method = RequestMethod.GET)
    public List<AvailabilityResponse> isAvailable(@RequestParam String date1, @RequestParam String date2) {
        // verify date range
        try {
            Date d1 = DATE_FORMAT.parse(date1);
            Date d2 = DATE_FORMAT.parse(date2);
            if (d1.after(d2)) {
                throw new IllegalArgumentException();
            }
            List<Availability> availabilities = this.availabilityRepository.findAvailability(d1, d2);
            if (availabilities!= null && !availabilities.isEmpty()) {
                List<AvailabilityResponse> list = new ArrayList<>();
                for (Availability a: availabilities) {
                    AvailabilityResponse ar = new AvailabilityResponse();
                    ar.date = a.date;
                    ar.slots = a.slots;
                    list.add(ar);
                }
                return list;
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException();
        }
        return null;
    }

    //remove this
    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public CampsiteResponse reserve(@RequestBody Reservation reservation) {
        // verify date range
        try {
            reservationRepository.save(reservation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CampsiteResponse();
    }

}
