package io.egen.service;

import io.egen.entity.Readings;

public interface ReadingsService{

    void insertReading(String data);
    Readings findReadings(String vinNo);

}
