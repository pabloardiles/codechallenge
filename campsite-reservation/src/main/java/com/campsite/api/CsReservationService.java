package com.campsite.api;

import com.campsite.model.Availability;
import com.campsite.model.Reservation;
import com.campsite.repository.AvailabilityRepository;
import com.campsite.repository.ReservationRepository;
import com.campsite.validator.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CsReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Transactional
    public String saveReservation(Reservation reservation) {
        List<Availability> list = availabilityRepository.findAvailability(reservation.getArrivalDate(), reservation.getDepartureDate());
        int dateCount = DateUtils.getDateCount(reservation.getArrivalDate(), reservation.getDepartureDate());
        if (list.isEmpty() || list.size() != dateCount) {
            throw new NoAvailabilityException("Some dates are not available for selection at the moment.");
        }
        list.forEach(a -> availabilityRepository.decrementSlots(a.getDate()));
        Reservation res = reservationRepository.save(reservation);
        return  res.getId();
    }

    @Transactional
    public String removeReservation(String id) {
        Optional<Reservation> res = reservationRepository.findById(id);
        if (!res.isPresent()) {
            throw new ReservationNotFoundException("Reservation " + id + " could not be found.");
        }
        List<Availability> list = availabilityRepository.findAvailability(res.get().getArrivalDate(), res.get().getDepartureDate());
        list.forEach(a -> availabilityRepository.incrementSlots(a.getDate()));
        reservationRepository.delete(res.get());
        return res.get().getId();
    }

}
