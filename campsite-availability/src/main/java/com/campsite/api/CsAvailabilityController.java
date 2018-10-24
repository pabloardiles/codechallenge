package com.campsite.api;

import com.campsite.error.CampsiteError;
import com.campsite.model.Availability;
import com.campsite.model.AvailabilityResponse;
import com.campsite.repository.AvailabilityRepository;
import com.campsite.validator.DateUtils;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "availability", description = "Endpoint for campsite availability")
@RequestMapping("/api")
public class CsAvailabilityController {

    private static final Logger logger = LogManager.getLogger(CsAvailabilityController.class);

    @Autowired
    private AvailabilityRepository availabilityRepository;


    @RequestMapping(value = "/availability", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get campsite availability", response = AvailabilityResponse.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of availabilities", response = AvailabilityResponse.class),
            @ApiResponse(code = 400, message = "Invalid parameters", response = CampsiteError.class)})
    public List<AvailabilityResponse> getAvailability(
            @ApiParam(value = "Date format YYYY-MM-DD", required = true)@RequestParam String from,
            @ApiParam(value = "Date format YYYY-MM-DD", required = true)@RequestParam(required = false) String to) {
        DateUtils.DateRange dateRange = DateUtils.getDateRange(from, to);
        List<Availability> availabilities = this.availabilityRepository.findAvailability(dateRange.from, dateRange.to);
        final List<AvailabilityResponse> list = new ArrayList<>();
        availabilities.forEach(a -> {
            AvailabilityResponse ar = new AvailabilityResponse();
            ar.setDate(a.getDate());
            ar.setSlots(a.getSlots());
            list.add(ar);});
        logger.info("Returning results: {}", list);
        return list;
    }

}
