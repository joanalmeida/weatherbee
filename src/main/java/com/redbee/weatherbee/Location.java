package com.redbee.weatherbee;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Location {
    private @Id
    @GeneratedValue Long location_id;
    private String name;

    private Location() {}

    public Location(String name) {
        this.name = name;
    }
}
