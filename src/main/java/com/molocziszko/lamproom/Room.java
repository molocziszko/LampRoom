package com.molocziszko.lamproom;

import javax.annotation.Resource;
import javax.persistence.*;

@Entity
@Table(name = "lamprooms", schema = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_name")
    private String name;

    @Enumerated(EnumType.STRING)
    private Country country;

    @Column(name = "is_switched")
    private boolean isLampOn;

    public Room(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public Room() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public boolean isLampOn() {
        return isLampOn;
    }

    public void setLampOn(boolean lampOn) {
        isLampOn = lampOn;
    }

    @Override
    public String toString() {
        return "You're now in the room " + name + ".\n"
                + "The room created from " + country + ".";
    }
}
