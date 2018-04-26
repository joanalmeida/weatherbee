package com.redbee.weatherbee;

import com.redbee.weatherbee.Entities.Location;
import com.redbee.weatherbee.Entities.User;
import com.redbee.weatherbee.Repositories.LocationRepository;
import com.redbee.weatherbee.Repositories.UserRepository;
import com.redbee.weatherbee.Services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner{
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final WeatherService weatherService;

    @Autowired
    public DatabaseLoader(LocationRepository locationRepository, UserRepository userRepository, WeatherService weatherService) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.weatherService = weatherService;
    }

    @Override
    public void run(String... strings) throws Exception {
        /*
        Location buenosAires = new Location("Buenos Aires");
        locationRepository.save(buenosAires);

        Location newYork = new Location("New York");
        locationRepository.save(newYork);

        User joan = new User("joan", "joan.almeida90@gmail.com", "321pepe");
        joan.getLocations().add(buenosAires);
        userRepository.save(joan);

        User pedro = new User("pedro", "pedro.sondeos@gmail.com", "pepe123");
        pedro.getLocations().add(buenosAires);
        pedro.getLocations().add(newYork);
        userRepository.save(pedro);
        */

        System.out.println("Saving initial dummy data");

        Location buenosAires = new Location("Buenos Aires");
        buenosAires.setImgUrl("https://www.cronista.com/__export/1512394972732/sites/diarioelcronista/img/2017/12/04/buenos_aires_hd_2_2.jpg_258117318.jpg");
        locationRepository.save(buenosAires);

        Location newYork = new Location("New York");
        newYork.setImgUrl("https://www.housingwire.com/ext/resources/images/editorial/A-New-Big-Images/states/New-York-City.jpg?1453402066");
        locationRepository.save(newYork);

        User joan = new User("joan", "joan.almeida90@gmail.com", "321pepe");
        joan.getLocations().add(buenosAires);
        userRepository.save(joan);

        /*
        Set<Location> locations = weatherService.getUpdatedLocations();
        for (Location loc : locations) {
            locationRepository.save(loc);
        }
        System.out.println("All Locations: ");
        System.out.println(locationRepository.findAll());
        */
        //System.out.println(weatherService.callYahooApi());
    }
}
