package com.example.smartcampus.config;

import com.example.smartcampus.resource.DiscoveryResource;
import com.example.smartcampus.resource.RoomResource;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
import com.example.smartcampus.resource.SensorResource;

public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(DiscoveryResource.class);
        classes.add(RoomResource.class);
        classes.add(SensorResource.class);
        return classes;
    }
}