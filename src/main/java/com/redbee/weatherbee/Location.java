package com.redbee.weatherbee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Location {
    @Id
    @GeneratedValue
    @Column(name = "location_id")
    private Long id;
    private String name;

    private Location() {}

    public Location(String name) {
        this.name = name;
    }
}
