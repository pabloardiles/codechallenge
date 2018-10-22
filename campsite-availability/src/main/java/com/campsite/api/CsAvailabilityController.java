package com.campsite.api;

import com.campsite.model.Availability;
import com.campsite.model.AvailabilityResponse;
import com.campsite.model.CampsiteResponse;
import com.campsite.model.Reservation;
import com.campsite.repository.AvailabilityRepository;
import com.campsite.repository.ReservationRepository;
import com.campsite.validator.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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


    @RequestMapping(value = "/availability", method = RequestMethod.GET)
    public List<AvailabilityResponse> getAvailability(@RequestParam String date1, @RequestParam String date2) {
        DateValidator.validateDateRange(date1, date2);
        LocalDate d1 = LocalDate.parse(date1);
        LocalDate d2 = LocalDate.parse(date2);
        List<Availability> availabilities = this.availabilityRepository.findAvailability(d1, d2);
        final List<AvailabilityResponse> list = new ArrayList<>();
        availabilities.forEach(a -> {
            AvailabilityResponse ar = new AvailabilityResponse();
            ar.date = a.date;
            ar.slots = a.slots;
            list.add(ar);});
        return list;
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
