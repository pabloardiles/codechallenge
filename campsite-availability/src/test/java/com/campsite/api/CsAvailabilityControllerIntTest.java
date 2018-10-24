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

/**
 * Integration tests for /api/availability resource using real database connection.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CsAvailabilityControllerIntTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldSucceedWhenDateRangeIsValid() {
        try {
            this.mvc.perform(get("/api/availability?from=2019-01-05&to=2019-01-25"))
                    .andExpect(status().isOk());
            this.mvc.perform(get("/api/availability?from=2019-01-05"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldFailWhenDateRangeIsInvalid() {
        try {
            this.mvc.perform(get("/api/availability?from=2019-01-25&to=2019-01-03"))
                    .andExpect(status().is4xxClientError());
            this.mvc.perform(get("/api/availability?from=2015-01-25&to=2015-01-26"))
                    .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldFailWhenInsufficientParameters() {
        try {
            this.mvc.perform(get("/api/availability?to=2019-01-03"))
                    .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
