package com.molocziszko.lamproom.service;

import com.molocziszko.lamproom.model.Room;
import com.molocziszko.lamproom.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository repository;

    @Autowired
    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public Room getRoomById(Long id) {
        return repository.getById(id);
    }

    public List<Room> getAllRooms() {
        return repository.findAll();
    }

    public Room save(Room room) {
        return repository.save(room);
    }

    public void updateLamp(Room userAccount, Long id) {
        Room roomToUpdate = getRoomById(id);
        roomToUpdate.setLampOn(userAccount.isLampOn());

        save(roomToUpdate);
    }
}
