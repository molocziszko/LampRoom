package com.molocziszko.lamproom.controllers;

import com.molocziszko.lamproom.model.Country;
import com.molocziszko.lamproom.model.Room;
import com.molocziszko.lamproom.service.RequestService;
import com.molocziszko.lamproom.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RoomControllerTest {

    private MockMvc mockMvc;
    private MockHttpServletRequest request;

    @Mock
    private RoomService roomService;

    @Mock
    private RequestService requestService;

    List<Room> list;

    @BeforeEach
    void init() {
        final RoomController controller = new RoomController(roomService, requestService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRemoteAddr("127.0.0.1");

        list = new ArrayList<>();
        Room room = new Room("afric999", Country.TANZANIA);
        room.setId(99L);
        list.add(room);


    }

    @Test
    void listOfRooms() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders
                .get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("roomsList"));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/rooms"))
                .andExpect(status().isOk())
                .andExpect(view().name("roomsList"));
    }

    @Test
    void openRoomCreator() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/rooms/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("new_room"));
    }

    @Test
    void createRoom() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/rooms/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rooms"));
    }

    @Test
    @Disabled("Until NPE in RoomController's clientIP will be prevent")
    void enterRoom() throws Exception {
        when(roomService.getRoomById(99L)).thenReturn(list.get(0));
        final Long USER_ID = list.get(0).getId();
        String ip = "2.17.250.255";
        when(requestService.getClientIp(request)).thenReturn(ip);
        when(requestService.checkClientLocation(ip)).thenReturn(list.get(0).getCountry().toString());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/rooms/{id}", USER_ID, request))
                .andExpect(status().isOk())
                .andExpect(view().name("room"));
    }

    @Test
    void switchLamp() throws Exception {
        final Long USER_ID = list.get(0).getId();

        mockMvc.perform(MockMvcRequestBuilders
                .put("/rooms/{id}", USER_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("room"));
    }
}