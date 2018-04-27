package com.redbee.weatherbee.Services;

import com.redbee.weatherbee.Entities.Location;
import org.springframework.context.ApplicationEvent;

import java.util.Set;

public class LocationEvent extends ApplicationEvent {
    private Set<Location> locations;

    public LocationEvent(Object source, Set<Location> locations) {
        super(source);
        this.locations = locations;
    }

    public Set<Location> getLocations() {
        return locations;
    }
}
