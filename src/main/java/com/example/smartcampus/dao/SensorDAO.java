package com.example.smartcampus.dao;

import com.example.smartcampus.model.Sensor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SensorDAO {

    private static final Map<String, Sensor> sensors = new LinkedHashMap<>();

    public Collection<Sensor> getAllSensors() {
        return sensors.values();
    }

    public Sensor getSensorById(String id) {
        return sensors.get(id);
    }

    public Sensor addSensor(Sensor sensor) {
        sensors.put(sensor.getId(), sensor);
        return sensor;
    }

    public boolean exists(String id) {
        return sensors.containsKey(id);
    }

    public List<Sensor> getSensorsByType(String type) {
        List<Sensor> filteredSensors = new ArrayList<>();

        for (Sensor sensor : sensors.values()) {
            if (sensor.getType() != null && sensor.getType().equalsIgnoreCase(type)) {
                filteredSensors.add(sensor);
            }
        }

        return filteredSensors;
    }

    public List<Sensor> getSensorsByRoomId(String roomId) {
        List<Sensor> roomSensors = new ArrayList<>();

        for (Sensor sensor : sensors.values()) {
            if (sensor.getRoomId() != null && sensor.getRoomId().equals(roomId)) {
                roomSensors.add(sensor);
            }
        }

        return roomSensors;
    }

    public Sensor deleteSensor(String id) {
        return sensors.remove(id);
    }
}