package com.redbee.weatherbee.Services;

import com.redbee.weatherbee.Entities.Forecast;
import com.redbee.weatherbee.Entities.Location;
import com.redbee.weatherbee.Repositories.ForecastRepository;
import com.redbee.weatherbee.Repositories.LocationRepository;
import com.redbee.weatherbee.Services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class LocationUpdater {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ForecastRepository forecastRepository;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    //1 min -> 60000
    //2 min -> 120000
    //10 min -> 600000
    //20 min -> 1200000
    //4hrs -> 14400000
    @Scheduled(fixedDelay = 120000, initialDelay = 10000)
    public void updateLocations() {
        //Getting All location names
        System.out.println("----------------------");
        System.out.println("Gathering all locations names");
        List<String> locationNames = locationRepository.getAllLocationsNames();
        System.out.println(locationNames);

        //Calling yahoo to get all location data
        System.out.println("Calling yahoo services");
        Set<Location> updatedLocations = weatherService.getUpdatedLocations(locationNames);

        if(updatedLocations != null) {
            //Saving
            System.out.println("Updating all locations");
            updateLocations(updatedLocations);
            System.out.println("Locations updated");
            System.out.println("----------------------");
        }
    }

    private void updateLocations(Set<Location> locations) {
        Set<Location> updatedLocs = new HashSet<>();
        Date now = new Date();
        for (Location loc : locations) {
            //Siempre existe
            Location existentLocation = locationRepository.findByName(loc.getName());
            //Puedo hacer algun check para ver si es necesario actualizar, pero mientras tanto actualizo siempre
            Set<Forecast> savedForecasts = new HashSet<>();
            for (Forecast forecast : loc.getForecasts()) {
                forecast = forecastRepository.save(forecast);
                savedForecasts.add(forecast);
            }
            existentLocation.setForecasts(savedForecasts);
            existentLocation.setCondition(loc.getCondition());
            //No pude hacer funcionar el PreUpdate, so...
            existentLocation.setUpdatedAt(now);
            existentLocation = locationRepository.save(existentLocation);
            updatedLocs.add(existentLocation);
            //Refactor
        }
        applicationEventPublisher.publishEvent(new LocationEvent(this, updatedLocs));
    }
}
