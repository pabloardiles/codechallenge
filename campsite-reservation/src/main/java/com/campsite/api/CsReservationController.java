package com.campsite.api;

import com.campsite.error.CampsiteError;
import com.campsite.model.Reservation;
import com.campsite.model.ReservationResponse;
import com.campsite.repository.AvailabilityRepository;
import com.campsite.repository.ReservationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "reservation", description = "Endpoint for campsite reservations")
@RequestMapping("/api")
public class CsReservationController {

    private static final Logger logger = LogManager.getLogger(CsReservationController.class);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @RequestMapping(value = "/reserve", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Save reservation", response = ReservationResponse.class, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reservation created successfully", response = ReservationResponse.class),
            @ApiResponse(code = 400, message = "Invalid parameters", response = CampsiteError.class)})
    public ReservationResponse reserve(@RequestBody Reservation reservation) {
        // verify reservation is 3 days
        // verify from < to
        // verify allowed period
        // verify availability
        try {
            reservationRepository.save(reservation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReservationResponse();
    }

}
