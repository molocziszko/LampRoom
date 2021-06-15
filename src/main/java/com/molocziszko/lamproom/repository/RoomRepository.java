package com.molocziszko.lamproom.repository;

import com.molocziszko.lamproom.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
