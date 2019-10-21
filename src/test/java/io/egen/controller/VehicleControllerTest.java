package io.egen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.egen.entity.Vehicle;
import io.egen.repository.VehicleRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.regex.Matcher;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
public class VehicleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VehicleRepository repository;

    @Before
    public void setUp(){

        Vehicle v = new Vehicle();
        v.setVin("1HGCR2F3XFA027534");
        v.setMake("HONDA");
        v.setModel("ACCORD");
        v.setYear(2015);
        v.setRedlineRpm(5500);
        v.setMaxFuelVolume(15);
        v.setLastServiceDate("2017-05-25T17:31:25.268Z");
        repository.save(v);
    }

    @After
    public void cleanUp(){
        repository.deleteAll();
    }

    @Test
    public void insertVehicle() throws Exception{
        String data = "[{\n" +
                "    \"vin\": \"WP1AB29P63LA60179\",\n" +
                "    \"make\": \"PORSCHE\",\n" +
                "    \"model\": \"CAYENNE\",\n" +
                "    \"year\": 2015,\n" +
                "    \"redlineRpm\": 8000,\n" +
                "    \"maxFuelVolume\": 18,\n" +
                "    \"lastServiceDate\": \"2017-03-25T17:31:25.268Z\"\n" +
                " }]";

        mvc.perform(MockMvcRequestBuilders.put("/vehicles")
                                          .contentType(MediaType.APPLICATION_JSON )
                                          .content(data))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //Before runs and creates an object and now the above method has sent another object so size is two and check for that
        mvc.perform(MockMvcRequestBuilders.get("/vehicles"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));

    }

    @Test
    public void findOne() throws Exception{

        mvc.perform(MockMvcRequestBuilders.get("/vehicles/1HGCR2F3XFA027534"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("1HGCR2F3XFA027534")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.make", Matchers.is("HONDA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Matchers.is("ACCORD")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year", Matchers.is(2015)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.redlineRpm", Matchers.is(5500)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxFuelVolume", Matchers.is(15)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastServiceDate", Matchers.is("2017-05-25T17:31:25.268Z")));
    }

    @Test
    public void findOne404() throws Exception{

        mvc.perform(MockMvcRequestBuilders.get("/vehicles/randomId"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void findAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].make", Matchers.is("HONDA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model", Matchers.is("ACCORD")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].year", Matchers.is(2015)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].redlineRpm", Matchers.is(5500)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].maxFuelVolume", Matchers.is(15)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastServiceDate", Matchers.is("2017-05-25T17:31:25.268Z")));
    }
}