package io.egen.service;

import io.egen.entity.Vehicle;

import java.util.List;

public interface VehicleService {

    Vehicle findOne(String id);
    void insertVehicle(String data);
    List<Vehicle> findAll();
}
