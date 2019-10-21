package io.egen.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.egen.entity.Vehicle;
import io.egen.exception.BadRequestException;
import io.egen.repository.VehicleRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Transactional
    public void insertVehicle(String data)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            Vehicle[] vehicles = objectMapper.readValue(data, Vehicle[].class);
            for(Vehicle v: vehicles )
            vehicleRepository.save(v);
        }
        catch(Exception e)
        {
            throw new BadRequestException("Please check input data format");
        }

    }

    @Transactional
    public Vehicle findOne(String vehId)
    {
        Optional<Vehicle> vehicle =vehicleRepository.findById(vehId);
        if(!vehicle.isPresent())
        {
            throw new BadRequestException("Vehicle with vin "+vehId+" not found");
        }
        else
            return vehicle.get();
    }

    @Transactional
    public List<Vehicle> findAll()
    {
        return (List<Vehicle>) vehicleRepository.findAll();
    }
}
