package com.campsite.api;

import com.campsite.error.CampsiteError;
import com.campsite.model.Reservation;
import com.campsite.model.ReservationRequest;
import com.campsite.model.ReservationResponse;
import com.campsite.validator.DateUtils;
import io.swagger.annotations.*;
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

    @RequestMapping(value = "/cancel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Cancel reservation", response = ReservationResponse.class, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reservation cancelled successfully", response = ReservationResponse.class),
            @ApiResponse(code = 404, message = "Reservation not found", response = CampsiteError.class)
    })
    public ReservationResponse cancel(
            @ApiParam(value = "e.g. 5bd07e81ccc3a4033c143acf", required = true)@RequestParam String reservationId) {
        String id = reservationService.removeReservation(reservationId);
        return new ReservationResponse(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update reservation (replace the previous one)", response = ReservationResponse.class, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reservation cancelled successfully", response = ReservationResponse.class),
            @ApiResponse(code = 400, message = "Invalid request body", response = CampsiteError.class),
            @ApiResponse(code = 404, message = "Reservation not found", response = CampsiteError.class),
            @ApiResponse(code = 409, message = "No availability for the selected dates", response = CampsiteError.class),
            @ApiResponse(code = 409, message = "One or more dates are not available at the time. Try again later.", response = CampsiteError.class)
    })
    public ReservationResponse update(@Valid @RequestBody ReservationRequest reservationRequest) {
        if (reservationRequest.getId() == null) {
            throw new IllegalArgumentException("id is required");
        }
        DateUtils.validateDateRange(reservationRequest.getArrivalDate(), reservationRequest.getDepartureDate());
        Reservation res = new Reservation();
        res.setId(reservationRequest.getId());
        res.setFirstName(reservationRequest.getFirstName());
        res.setLastName(reservationRequest.getLastName());
        res.setEmail(reservationRequest.getEmail());
        res.setArrivalDate(reservationRequest.getArrivalDate());
        res.setDepartureDate(reservationRequest.getDepartureDate());
        String id = reservationService.updateReservation(res);
        return new ReservationResponse(id);
    }
}
