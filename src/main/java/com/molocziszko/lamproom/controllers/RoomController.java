package com.molocziszko.lamproom.controllers;

import com.molocziszko.lamproom.model.Room;
import com.molocziszko.lamproom.service.RequestService;
import com.molocziszko.lamproom.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping({"/", "/rooms"})
public class RoomController {

    private final RoomService service;
    private final RequestService requestService;

    @Autowired
    public RoomController(RoomService service, RequestService requestService) {
        this.service = service;
        this.requestService = requestService;
    }

    @GetMapping()
    public String listOfRooms(Model model) {
        List<Room> rooms = service.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "rooms";
    }

    @GetMapping("/new")
    public String openRoomCreator(Room room) {
        return "new";
    }

    @PostMapping("/new")
    public String createRoom(Room room) {
        service.save(room);

        return "redirect:/rooms";
    }

    @GetMapping("/{id}")
    public String enterRoom(@PathVariable Long id, Model model, HttpServletRequest request) {
        String clientIP = requestService.getClientIp(request);
        if (!requestService.isLocalhost(clientIP)) {
            Room room = service.getRoomById(id);
            String clientCountry = requestService.checkClientLocation(clientIP);
            if (!clientCountry.equalsIgnoreCase(room.getCountry().toString())) {
                model.addAttribute("clientCountry", clientCountry);
                model.addAttribute("roomCountry", room.getCountry());
                return "badLocation";
            }
        }
        model.addAttribute("room", service.getRoomById(id));
        return "room";
    }

    @PutMapping("/{id}")
    public String switchLamp(@PathVariable("id") Long id,
                             @RequestParam(value = "lampOn", required = false) String lampOn,
                             @RequestParam(value = "lampOff", required = false) String lampOff,
                             @ModelAttribute(value = "room") Room room) {
        if (lampOn == "On") {
            room.setLampOn(true);
        }
        if (lampOff == "Off") {
            room.setLampOn(false);
        }
        service.updateLamp(room, id);

        return "room";
    }
}
