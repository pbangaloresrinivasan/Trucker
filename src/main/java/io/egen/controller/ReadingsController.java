package io.egen.controller;

import io.egen.entity.Readings;
import io.egen.service.ReadingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="readings")
@Api(value = "/readings", description="Operations on readings")
public class ReadingsController {

    @Autowired
    private ReadingsService readingsService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Insert readings",
            notes = "Send in new readings of vehicles to the DB")
    public void insertReading(@RequestBody String data)
    {
        readingsService.insertReading(data);
    }

    @RequestMapping(method = RequestMethod.GET, value="{vin}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Fetch readings",
            notes = "Fetch the details of particular vehicle's readings with given vin from DB")
    public Readings getReadings(@PathVariable("vin") String vinNo)
    {
        return  readingsService.findReadings(vinNo);
    }
}
