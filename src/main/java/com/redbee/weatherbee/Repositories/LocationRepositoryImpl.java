package com.redbee.weatherbee.Repositories;

import com.redbee.weatherbee.Entities.Location;
import com.redbee.weatherbee.Repositories.LocationRepository;
import com.redbee.weatherbee.Repositories.LocationRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LocationRepositoryImpl implements LocationRepositoryCustom {

    @Autowired
    LocationRepository locationRepository;

    @Override
    public List<String> getAllLocationsNames() {
        Set<Location> locations = locationRepository.findAll();
        List<String> names = new ArrayList<>();
        for (Location loc : locations) {
            names.add(loc.getName());
        }

        return names;
    }
}
