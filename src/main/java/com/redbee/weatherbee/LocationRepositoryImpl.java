package com.redbee.weatherbee;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class LocationRepositoryImpl implements LocationRepositoryCustom {

    @Autowired
    LocationRepository locationRepository;

    @Override
    public Set<String> getAllLocationsNames() {
        Set<Location> locations = locationRepository.findAll();
        Set<String> names = new HashSet<>();
        for (Location loc : locations) {
            names.add(loc.getName());
        }

        return names;
    }
}
