package com.campsite.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CsReservationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldSucceedWhenDateRangeIsValid() {
        try {
            this.mvc.perform(get("/api/availability?date1=2019-01-05&date2=2019-01-25"))
                    .andExpect(status().isOk());
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.arrivalDate", is("2019-01-05")))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.departureDate", is("2019-01-25")));;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldFailWhenDateRangeIsInvalid() {
        try {
            this.mvc.perform(get("/api/availability?date1=2019-01-25&date2=2019-01-03"))
                    .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldFailWhenInsufficientParameters() {
        try {
            this.mvc.perform(get("/api/availability?date2=2019-01-03"))
                    .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldSucceedIfReservationNoLongerThanThreeDays() {
//        assertThat(controller).isNotNull();
        // assert code 200
//        try {
//            this.mvc.perform(get("/api/available?date1=2019-01-05&date2=2019-01-25"))
//                    .andExpect(status().isOk())
////                    .andExpect(content().json("{arrivalDate: 2019-01-05, departureDate: 2019-01-25}"))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.arrivalDate", is("2019-01-05")))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.departureDate", is("2019-01-25")));;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void shouldFailIfReservationLongerThanThreeDays() {
        //assert code 400
    }

    @Test
    public void shouldSucceedIfReservationIsBookedInValidPeriod() {
        // assert code 200
    }

    @Test
    public void shouldFailIfReservationIsBookedInInvalidPeriod() {
        // assert code 400
    }

    @Test
    public void shouldHandleOverlappedReservations() {
        // assert status code conflict
    }

}
