package io.egen.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.egen.entity.Alert;
import io.egen.entity.Readings;
import io.egen.entity.Tires;
import io.egen.entity.Vehicle;
import io.egen.exception.BadRequestException;
import io.egen.repository.ReadingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
public class ReadingsServiceImpl implements ReadingsService {

    @Autowired
    private ReadingsRepository readingsRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private AlertService alertService;

    @Transactional
    public void insertReading(String data)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            Readings readings = objectMapper.readValue(data, Readings.class);
            Vehicle vehicle = vehicleService.findOne(readings.getVin());
            if(vehicle!=null)
            {
                checkAlerts(readings,vehicle);
                readingsRepository.save(readings);
            }
        }
        catch(IOException e)
        {
            throw new BadRequestException("Please check the readings data input format");
        }
    }

    @Transactional
    public Readings findReadings(String vinNo)
    {
        Optional<Readings> readings = readingsRepository.findByVin(vinNo);
        return readings.get();
    }

    @Async
    public void checkAlerts(Readings readings, Vehicle vehicle) {

        Tires tires = readings.getTires();
        int[] pressures = { tires.getFrontLeft(), tires.getFrontRight(), tires.getRearLeft(), tires.getRearRight()};

        if(readings.getEngineRpm() > vehicle.getRedlineRpm())
        {
            Alert alert = new Alert();
            alert.setVin(vehicle.getVin());
            alert.setAlertType("HIGH");
            alert.setReason("Engine Rpm Higher than RedLine Rpm");
            alertService.insertAlert(alert);
        }

        if(readings.getFuelVolume() < (0.10 * vehicle.getMaxFuelVolume()))
        {
            Alert alert = new Alert();
            alert.setVin(vehicle.getVin());
            alert.setAlertType("MEDIUM");
            alert.setReason("Fuel Volume is less than 10% of Max storage");
            alertService.insertAlert(alert);
        }

        for(int p : pressures)
        {
            if(p>36 || p<32)
            {
                Alert alert = new Alert();
                alert.setVin(vehicle.getVin());
                alert.setAlertType("LOW");
                if(p<32)
                    alert.setReason("Tire pressure low");
                else
                    alert.setReason("TirePressure high");
                alertService.insertAlert(alert);
            }
        }

        if(readings.isEngineCoolantLow() || readings.isCheckEngineLightOn())
        {
            Alert alert = new Alert();
            alert.setVin(vehicle.getVin());
            alert.setAlertType("LOW");
            if(readings.isEngineCoolantLow())
                alert.setReason("Engine Coolant is low");
            else
                alert.setReason("Check Engine Light is on");
            alertService.insertAlert(alert);
        }
    }
}
