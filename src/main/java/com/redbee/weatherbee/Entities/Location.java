package com.redbee.weatherbee.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
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

    private Date createdAt;

    private Date updatedAt;

    @Embedded
    private Condition condition;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Forecast> forecasts;

    private Location() {}

    public Location(String name) {
        this.name = name;
    }

    @PrePersist
    public void addCreatedDate() {
        Date now = new Date();
        createdAt = now;
        updatedAt = now;
    }
}
