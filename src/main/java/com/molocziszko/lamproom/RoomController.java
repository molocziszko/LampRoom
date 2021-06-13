package com.molocziszko.lamproom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class RoomController {

    private final RoomService service;

    @Autowired
    public RoomController(RoomService service) {
        this.service = service;
    }

    public String index() {return "";
    }

    @GetMapping("rooms")
    public String listOfRooms(Model model) {
        List<Room> rooms = service.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "rooms";
    }

    @GetMapping("rooms/new")
    public String openRoomCreator(Room room) {
        return "new";
    }

    @PostMapping("rooms/new")
    public String createRoom(Room room) {
        service.save(room);

        return "redirect:/rooms";
    }

    @GetMapping("rooms/{id}")
    public String enterRoom(@PathVariable("id") Long id, Model model) {
        model.addAttribute("room", service.getRoomById(id));
        return "room";
    }
}
