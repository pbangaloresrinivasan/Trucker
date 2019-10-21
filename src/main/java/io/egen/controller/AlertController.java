package io.egen.controller;

import io.egen.entity.Alert;
import io.egen.service.AlertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping(value = "alerts")
@Api(value = "/alerts", description="Operations on alerts")
public class AlertController {

    @Autowired
    AlertService alertService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find all alerts",
            notes = "Fetch all the alerts of all vehicles from the DB")
    public List<Alert> allAlerts()
    {
        return alertService.allAlerts();
    }

    @RequestMapping(method = RequestMethod.GET, value="{vin}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find specific alert",
            notes = "Fetch all the alerts of a particular vehicle with given vin from DB")
    public List<Alert> findOne(@PathVariable("vin") String vinNo)
    {
        return alertService.findAllByVin(vinNo);
    }
}
