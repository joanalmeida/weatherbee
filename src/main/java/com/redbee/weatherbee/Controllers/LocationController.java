package com.redbee.weatherbee.Controllers;

import com.redbee.weatherbee.Entities.Forecast;
import com.redbee.weatherbee.Entities.Location;
import com.redbee.weatherbee.Entities.LocationDTO;
import com.redbee.weatherbee.Repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;

@Controller
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    //For autocomplete component
    @CrossOrigin
    @RequestMapping(value = "/api/location/all", method = RequestMethod.GET)
    public ResponseEntity<Set<LocationDTO>> getAllLocations() {
        Set<LocationDTO> locations = new HashSet<>();
        Set<Location> allLocations = locationRepository.findAll();
        for (Location loc : allLocations) {
            LocationDTO locDTO = new LocationDTO(loc.getId(), loc.getName());
            locations.add(locDTO);
        }
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/location/{id}/forecast", method = RequestMethod.GET)
    public ResponseEntity<Set<Forecast>> getForecast(@PathVariable("id") Long id) {
        Set<Forecast> forecasts = locationRepository.findById(id).getForecasts();
        return new ResponseEntity<>(forecasts, HttpStatus.OK);
    }
}
