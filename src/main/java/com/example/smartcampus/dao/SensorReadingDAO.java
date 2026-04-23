package com.example.smartcampus.dao;

import com.example.smartcampus.model.SensorReading;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SensorReadingDAO {

    private static final Map<String, List<SensorReading>> sensorReadings = new LinkedHashMap<>();

    public List<SensorReading> getReadingsBySensorId(String sensorId) {
        return sensorReadings.getOrDefault(sensorId, new ArrayList<>());
    }

    public SensorReading addReading(String sensorId, SensorReading reading) {
        sensorReadings.putIfAbsent(sensorId, new ArrayList<>());
        sensorReadings.get(sensorId).add(reading);
        return reading;
    }
}