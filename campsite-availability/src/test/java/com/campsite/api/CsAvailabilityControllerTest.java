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
//@ContextConfiguration(classes = CampsiteApiApplication.class)
//@AutoConfigureWebTestClient
//@Import(CampsiteApiApplication.class)
//@RunWith(SpringRunner.class)
////@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebMvcTest(CsAvailabilityController.class)
@AutoConfigureMockMvc
//@JsonTest
public class CsAvailabilityControllerTest {

//    @Autowired
//    private CsAvailabilityController controller;

    @Autowired
    private MockMvc mvc;
//
//    @Autowired
//    private JacksonTester<CampsiteResponse> json;

//    @Test
//    public void contextLoads() {
//        assertThat(controller).isNotNull();
//    }

//    @Autowired
//    private WebTestClient webClient;



    ////////////////////////////////////
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;


    @Test
    public void shouldSucceedWhenDateRangeIsValid() {
        try {
            this.mvc.perform(get("/api/available?date1=2019-01-05 00:00:00.000&date2=2019-01-25 00:00:00.000"))
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
            this.mvc.perform(get("/api/available?date1=2019-01-25 00:00:00.000&date2=2019-01-03 00:00:00.000"))
                    .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldSucceedIfReservationNoLongerThanThreeDays() {
//        assertThat(controller).isNotNull();
        // assert code 200
        try {
            this.mvc.perform(get("/api/available?date1=2019-01-05&date2=2019-01-25"))
                    .andExpect(status().isOk())
//                    .andExpect(content().json("{arrivalDate: 2019-01-05, departureDate: 2019-01-25}"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.arrivalDate", is("2019-01-05")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.departureDate", is("2019-01-25")));;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void exampleTest() {
//        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/available?date1=2019-01-05&date2=2019-01-25",
//                String.class)).contains("Hello World");
//        try {
//
//

//            CampsiteResponse response = new CampsiteResponse();
//            this.webClient.get().uri("/api/available?date1=2019-01-05&date2=2019-01-25")
//                    .exchange()
//                    .expectStatus().isOk()
//                    .expectBody(String.class).isEqualTo(this.json.write(response).getJson());
//        } catch (IOException e) {
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
