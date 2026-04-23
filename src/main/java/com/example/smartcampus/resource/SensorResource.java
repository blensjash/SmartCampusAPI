package com.example.smartcampus.resource;

import com.example.smartcampus.dao.RoomDAO;
import com.example.smartcampus.dao.SensorDAO;
import com.example.smartcampus.dao.SensorReadingDAO;
import com.example.smartcampus.model.Room;
import com.example.smartcampus.model.Sensor;
import com.example.smartcampus.model.SensorReading;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    private SensorDAO sensorDAO = new SensorDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private SensorReadingDAO sensorReadingDAO = new SensorReadingDAO();

    @GET
    public Response getAllSensors(@QueryParam("type") String type) {
        if (type != null && !type.trim().isEmpty()) {
            List<Sensor> filtered = sensorDAO.getSensorsByType(type);
            return Response.ok(filtered).build();
        }

        Collection<Sensor> sensors = sensorDAO.getAllSensors();
        return Response.ok(sensors).build();
    }

    @POST
    public Response createSensor(Sensor sensor, @Context UriInfo uriInfo) {
        if (sensor == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (sensor.getId() == null || sensor.getId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Sensor ID is required")
                    .build();
        }

        if (sensor.getType() == null || sensor.getType().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Sensor type is required")
                    .build();
        }

        if (sensor.getRoomId() == null || sensor.getRoomId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Room ID is required")
                    .build();
        }

        if (sensorDAO.exists(sensor.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Sensor already exists")
                    .build();
        }

        Room room = roomDAO.getRoomById(sensor.getRoomId());
        if (room == null) {
            return Response.status(422)
                    .entity("Room does not exist")
                    .build();
        }

        sensorDAO.addSensor(sensor);
        room.getSensorIds().add(sensor.getId());

        URI uri = uriInfo.getAbsolutePathBuilder().path(sensor.getId()).build();
        return Response.created(uri).entity(sensor).build();
    }

    @GET
    @Path("/{sensorId}/readings")
    public Response getReadings(@PathParam("sensorId") String sensorId) {
        Sensor sensor = sensorDAO.getSensorById(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        List<SensorReading> readings = sensorReadingDAO.getReadingsBySensorId(sensorId);
        return Response.ok(readings).build();
    }

    @POST
    @Path("/{sensorId}/readings")
    public Response addReading(@PathParam("sensorId") String sensorId,
                               SensorReading reading,
                               @Context UriInfo uriInfo) {
        Sensor sensor = sensorDAO.getSensorById(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        if (reading == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Reading data is required")
                    .build();
        }

        if (reading.getId() == null || reading.getId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Reading ID is required")
                    .build();
        }

        sensorReadingDAO.addReading(sensorId, reading);
        sensor.setCurrentValue(reading.getValue());

        URI uri = uriInfo.getAbsolutePathBuilder().path(reading.getId()).build();
        return Response.created(uri).entity(reading).build();
    }
}