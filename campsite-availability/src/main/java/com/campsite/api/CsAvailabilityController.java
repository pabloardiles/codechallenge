package com.campsite.api;

import com.campsite.error.CampsiteError;
import com.campsite.model.Availability;
import com.campsite.model.AvailabilityResponse;
import com.campsite.model.CampsiteResponse;
import com.campsite.model.Reservation;
import com.campsite.repository.AvailabilityRepository;
import com.campsite.repository.ReservationRepository;
import com.campsite.validator.DateValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "availability", description = "Endpoint for campsite availability")
@RequestMapping("/api")
public class CsAvailabilityController {

    private static final Logger logger = LogManager.getLogger(CsAvailabilityController.class);

    @Autowired
    private ReservationRepository reservationRepository; //remove this

    @Autowired
    private AvailabilityRepository availabilityRepository;


    @RequestMapping(value = "/availability", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get campsite availability", response = AvailabilityResponse.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of availabilities", response = AvailabilityResponse.class),
            @ApiResponse(code = 400, message = "Invalid parameters", response = CampsiteError.class)})
    public List<AvailabilityResponse> getAvailability(@RequestParam String date1, @RequestParam String date2) {
        DateValidator.validateDateRange(date1, date2);
        LocalDate d1 = LocalDate.parse(date1);
        LocalDate d2 = LocalDate.parse(date2);
        List<Availability> availabilities = this.availabilityRepository.findAvailability(d1, d2);
        final List<AvailabilityResponse> list = new ArrayList<>();
        availabilities.forEach(a -> {
            AvailabilityResponse ar = new AvailabilityResponse();
            ar.setDate(a.getDate());
            ar.setSlots(a.getSlots());
            list.add(ar);});
        logger.info("Returning results: {}", list);
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
