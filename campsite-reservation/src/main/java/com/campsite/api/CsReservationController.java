package com.campsite.api;

import com.campsite.error.CampsiteError;
import com.campsite.model.Reservation;
import com.campsite.model.ReservationRequest;
import com.campsite.model.ReservationResponse;
import com.campsite.validator.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(value = "reservation", description = "Endpoint for campsite reservations")
@RequestMapping("/api")
public class CsReservationController {

    private static final Logger logger = LogManager.getLogger(CsReservationController.class);

    @Autowired
    private CsReservationService reservationService;

    @RequestMapping(value = "/reserve", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Save reservationRequest", response = ReservationResponse.class, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reservation created successfully", response = ReservationResponse.class),
            @ApiResponse(code = 400, message = "Invalid request body", response = CampsiteError.class),
            @ApiResponse(code = 409, message = "No availability for the selected dates", response = CampsiteError.class),
            @ApiResponse(code = 409, message = "One or more dates are not available at the time. Try again later.", response = CampsiteError.class)
    })
    public ReservationResponse reserve(@Valid @RequestBody ReservationRequest reservationRequest) {
        DateUtils.validateDateRange(reservationRequest.getArrivalDate(), reservationRequest.getDepartureDate());
        Reservation res = new Reservation();
        res.setFirstName(reservationRequest.getFirstName());
        res.setLastName(reservationRequest.getLastName());
        res.setEmail(reservationRequest.getEmail());
        res.setArrivalDate(reservationRequest.getArrivalDate());
        res.setDepartureDate(reservationRequest.getDepartureDate());
        String id = reservationService.saveReservation(res);
        return new ReservationResponse(id);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Cancel reservation", response = ReservationResponse.class, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reservation cancelled successfully", response = ReservationResponse.class),
            @ApiResponse(code = 404, message = "Reservation not found", response = CampsiteError.class)
    })
    public ReservationResponse cancel(@RequestParam String reservationId) {
        String id = reservationService.removeReservation(reservationId);
        return new ReservationResponse(id);
    }

}
