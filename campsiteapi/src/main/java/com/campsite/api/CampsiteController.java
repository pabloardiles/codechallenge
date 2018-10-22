package com.campsite.api;

import com.campsite.model.CampsiteResponse;
import com.campsite.model.Reservation;
import com.campsite.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CampsiteController {

    @Autowired
    private ReservationRepository reservationRepository;

    @RequestMapping(value = "/available", method = RequestMethod.GET)
    public CampsiteResponse isAvailable(@RequestParam String date1, @RequestParam String date2) {
        // verify date range
        List<Reservation> reservations = reservationRepository.findByArrivalDateAndDepartureDate(date1, date2);
        if (reservations!= null && !reservations.isEmpty()) {
            Reservation r = reservations.get(0);
            CampsiteResponse csr = new CampsiteResponse();
            csr.setArrivalDate(r.arrivalDate);
            csr.setDepartureDate(r.departureDate);
            return csr;
        }
        return null;
    }

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
