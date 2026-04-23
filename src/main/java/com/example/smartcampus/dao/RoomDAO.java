package com.example.smartcampus.dao;

import com.example.smartcampus.model.Room;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class RoomDAO {

    private static final Map<String, Room> rooms = new LinkedHashMap<>();

    public Collection<Room> getAllRooms() {
        return rooms.values();
    }

    public Room getRoomById(String id) {
        return rooms.get(id);
    }

    public Room addRoom(Room room) {
        rooms.put(room.getId(), room);
        return room;
    }

    public Room deleteRoom(String id) {
        return rooms.remove(id);
    }

    public boolean exists(String id) {
        return rooms.containsKey(id);
    }
}