package com.molocziszko.lamproom.service;

import com.molocziszko.lamproom.model.Country;
import com.molocziszko.lamproom.model.Room;
import com.molocziszko.lamproom.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RoomServiceTest {
    List<Room> list = new ArrayList<>();

    @Mock
    RoomRepository repository;

    @BeforeEach
    void init() {
        list.add(new Room("rus", Country.RUSSIAN_FEDERATION));
        list.add(new Room("bel", Country.BELARUS));
        list.add(new Room("belg", Country.BELGIUM));

        when(repository.findAll()).thenReturn(list);
        when(repository.save(list.get(2))).thenReturn(list.get(2));
        when(repository.getById(8L)).thenReturn(list.get(0));

    }

    @Test
    void getRoomById() {
        when(repository.getById(8L)).thenReturn(new Room("rus", Country.RUSSIAN_FEDERATION));
        Room room8 = this.repository.getById(8L);

        assertAll("rooms",
                () -> assertFalse(room8.isLampOn()),
                () -> {
                    String name = room8.getName();
                    assertNotNull(name);

                    assertAll("name",
                            () -> assertTrue(name.startsWith("r")),
                            () -> assertTrue(name.endsWith("s")),
                            () -> assertNotEquals("ras", name)
                    );
                },

                () -> {
                    Country country = room8.getCountry();
                    assertNotNull(country);

                    assertAll("country",
                            () -> assertNotEquals(Country.ZAMBIA, country),
                            () -> assertThat(country).isEqualByComparingTo(Country.RUSSIAN_FEDERATION)
                    );
                }
        );
    }

    @Test
    void getAllRooms() {
        RoomService service = new RoomService(repository);
        List<Room> testList = service.getAllRooms();

        assertAll("list",
                () -> assertNotNull(testList),
                () -> assertEquals(3, testList.size()),
                () -> assertEquals(list, testList)
        );
    }

    @Test
    void save() {
        RoomService service = new RoomService(repository);
        Room roomToSave = list.get(2);
        Room savedRoom = service.save(roomToSave);

        assertAll("rooms",
                () -> assertThat(savedRoom.getCountry()).isEqualByComparingTo(Country.BELGIUM),
                () -> assertFalse(savedRoom.isLampOn()),
                () -> assertEquals(roomToSave, savedRoom)
        );
    }

    @Test
    void updateLamp() {
        RoomService service = new RoomService(repository);
        Room expected = list.get(0);
        service.updateLamp(expected, 8L);

        Room actual = service.getRoomById(8L);

        assertEquals(expected, actual);
        assertEquals(expected.isLampOn(), actual.isLampOn());
    }
}