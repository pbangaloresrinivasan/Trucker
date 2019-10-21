package io.egen.controller;

import io.egen.entity.Vehicle;
import io.egen.service.VehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="vehicles")
@Api(value = "/vehicles", description="Operations on vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Insert vehicle data",
            notes = "All details about the vehicles is sent in an array of {} or single entry {}")
    public void insertVehicle(@RequestBody String data)
    { vehicleService.insertVehicle(data);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find vehicle data",
            notes = "Fetch the details of particular vehicle with given vin from DB")
    public Vehicle findOne(@PathVariable("id") String vehId)
    {
        return vehicleService.findOne(vehId);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find all vehicle data",
            notes = "Fetch the details of all vehicles from DB")
    public List<Vehicle> findAll(){
        return vehicleService.findAll();
    }



}
