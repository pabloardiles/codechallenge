package com.campsite.api;

import com.campsite.testutils.TestUtil;
import com.campsite.model.ReservationRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for /api/reserve, /api/cancel and /api/update resources using real database connection.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CsReservationControllerIntTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldSucceedIfReservationNoLongerThanThreeDays() {
        try {
            ReservationRequest rq = new ReservationRequest();
            rq.setFirstName("John");
            rq.setLastName("Smith");
            rq.setEmail("smith@somthing.com");
            LocalDate ld1 = LocalDate.now().plusDays(15);
            LocalDate ld2 = LocalDate.now().plusDays(17);
            rq.setArrivalDate(ld1);
            rq.setDepartureDate(ld2);
            this.mvc.perform(post("/api/reserve")
                        .content(TestUtil.convertObjectToJsonBytes(rq))
                        .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.reservationId", notNullValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldFailIfReservationLongerThanThreeDays() {
        try {
            ReservationRequest rq = new ReservationRequest();
            rq.setFirstName("John");
            rq.setLastName("Smith");
            rq.setEmail("smith@somthing.com");
            LocalDate ld1 = LocalDate.now().plusDays(15);
            LocalDate ld2 = LocalDate.now().plusDays(20);
            rq.setArrivalDate(ld1);
            rq.setDepartureDate(ld2);
            this.mvc.perform(post("/api/reserve")
                    .content(TestUtil.convertObjectToJsonBytes(rq))
                    .contentType("application/json"))
                    .andExpect(status().is4xxClientError())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error", is("Reservation cannot exceed 3 days")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldFailIfReservationIsBookedInInvalidPeriod() {
        try {
            ReservationRequest rq = new ReservationRequest();
            rq.setFirstName("John");
            rq.setLastName("Smith");
            rq.setEmail("smith@somthing.com");
            LocalDate ld1 = LocalDate.now();
            LocalDate ld2 = LocalDate.now();
            rq.setArrivalDate(ld1);
            rq.setDepartureDate(ld2);

            this.mvc.perform(post("/api/reserve")
                    .content(TestUtil.convertObjectToJsonBytes(rq))
                    .contentType("application/json"))
                    .andExpect(status().is4xxClientError())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error", is("Invalid allowed reservation period")));

            ld1 = LocalDate.now().plusMonths(2);
            ld2 = LocalDate.now().plusMonths(2);
            rq.setArrivalDate(ld1);
            rq.setDepartureDate(ld2);

            this.mvc.perform(post("/api/reserve")
                    .content(TestUtil.convertObjectToJsonBytes(rq))
                    .contentType("application/json"))
                    .andExpect(status().is4xxClientError())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error", is("Invalid allowed reservation period")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldSucceedCancelReservations() {
    }

    @Test
    public void shouldSucceedUpdateReservations() {
    }
}
