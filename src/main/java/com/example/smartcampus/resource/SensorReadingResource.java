package com.example.smartcampus.resource;

import com.example.smartcampus.dao.SensorDAO;
import com.example.smartcampus.dao.SensorReadingDAO;
import com.example.smartcampus.model.Sensor;
import com.example.smartcampus.model.SensorReading;
import java.net.URI;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private String sensorId;
    private SensorDAO sensorDAO = new SensorDAO();
    private SensorReadingDAO sensorReadingDAO = new SensorReadingDAO();

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public Response getReadings() {
        Sensor sensor = sensorDAO.getSensorById(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<SensorReading> readings = sensorReadingDAO.getReadingsBySensorId(sensorId);
        return Response.ok(readings).build();
    }

    @POST
    public Response addReading(SensorReading reading, @Context UriInfo uriInfo) {
        Sensor sensor = sensorDAO.getSensorById(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (reading == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        sensorReadingDAO.addReading(sensorId, reading);
        sensor.setCurrentValue(reading.getValue());

        URI uri = uriInfo.getAbsolutePathBuilder().path(reading.getId()).build();
        return Response.created(uri).entity(reading).build();
    }
}