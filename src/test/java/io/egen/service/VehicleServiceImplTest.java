package io.egen.service;

import io.egen.entity.Vehicle;
import io.egen.exception.BadRequestException;
import io.egen.repository.VehicleRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class VehicleServiceImplTest {

    @TestConfiguration
    static class VehicleServiceImplTestConfiguration{

        @Bean
        public VehicleService getService(){
            return new VehicleServiceImpl();
        }
    }

    @Autowired
    private VehicleService service;

    @MockBean
    private VehicleRepository repository;

    //private variable for testing i.e to mock returns from actual function executions
    private List<Vehicle> vehicles;
    private Vehicle vehicle;
    private String data = "[{\n" +
            "    \"vin\": \"1HGCR2F3XFA027534\",\n" +
            "    \"make\": \"HONDA\",\n" +
            "    \"model\": \"ACCORD\",\n" +
            "    \"year\": 2015,\n" +
            "    \"redlineRpm\": 5500,\n" +
            "    \"maxFuelVolume\": 15,\n" +
            "    \"lastServiceDate\": \"2017-05-25T17:31:25.268Z\"\n" +
            " }]";

    @Before
    public void setUp(){

        String vId = "any-id-is-fine";

        Vehicle v = new Vehicle();
        v.setVin("1HGCR2F3XFA027534");
        v.setMake("HONDA");
        v.setModel("ACCORD");
        v.setYear(2015);
        v.setRedlineRpm(5500);
        v.setMaxFuelVolume(15);
        v.setLastServiceDate("2017-05-25T17:31:25.268Z");

        //Defining this list to be holding hte above created object
        vehicles = Collections.singletonList(v);

        //Mockito's way saying when so-so line is called then return this value
        Mockito.when(repository.findAll())
                .thenReturn(vehicles);

        Mockito.when(repository.findById(v.getVin()))
                .thenReturn(Optional.of(v));

    }

    @After
    public void cleanUp(){

    }

    @Test
    public void insertVehicle() {
        service.insertVehicle(data);
    }

    @Test(expected = BadRequestException.class)
    public void insertVehicleWrongFormat() {
        service.insertVehicle("extra-value");
    }

    @Test
    public void findOne() throws Exception {
        Vehicle result = service.findOne(vehicles.get(0).getVin());
        Assert.assertEquals("Err: Vehicle details dont match",vehicles.get(0),result);
    }

    @Test(expected = BadRequestException.class)
    public void findOneNotFound() throws Exception {
        Vehicle result = service.findOne("wrong-id");
    }

    @Test
    public void findAll() {

        List<Vehicle> result = service.findAll();
        Assert.assertEquals("Err: The values do not match",vehicles,result);

    }
}