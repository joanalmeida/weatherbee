package com.redbee.weatherbee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Location {
    @Id
    @GeneratedValue
    @Column(name = "location_id")
    private Long id;
    private String name;
    private String imgUrl;

    @Embedded
    private Condition condition;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Forecast> forecasts;

    private Location() {}

    public Location(String name) {
        this.name = name;
    }
}
