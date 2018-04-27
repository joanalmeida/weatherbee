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
        //No crear nada si ya existen datos
        if (locationRepository.findAll().size() > 0) {
            return;
        }

        System.out.println("Saving initial dummy data");

        Location buenosAires = new Location("Buenos Aires");
        buenosAires.setImgUrl("https://www.cronista.com/__export/1512394972732/sites/diarioelcronista/img/2017/12/04/buenos_aires_hd_2_2.jpg_258117318.jpg");
        locationRepository.save(buenosAires);

        Location newYork = new Location("New York");
        newYork.setImgUrl("https://www.housingwire.com/ext/resources/images/editorial/A-New-Big-Images/states/New-York-City.jpg?1453402066");
        locationRepository.save(newYork);

        Location paris = new Location("Paris");
        paris.setImgUrl("http://europeanbusinessmagazine.com/wp-content/uploads/2017/07/paris.jpg");
        locationRepository.save(paris);

        Location vancouver = new Location("Vancouver");
        vancouver.setImgUrl("http://res.cloudinary.com/simpleview/image/upload/v1486505969/clients/vancouverbc/Aerial_Sunset_Vancouver_d3_copy_1bb86ed0-1edc-4cda-841d-0b033ca0bb72.jpg");
        locationRepository.save(vancouver);

        Location losAngeles = new Location("Los Angeles");
        losAngeles.setImgUrl("https://www.visittheusa.co/sites/default/files/styles/hero_m_1300x700/public/images/hero_media_image/2016-10/HERO%201_LosAngeles_EDITORIAL_shutterstock_334078379_CROP_Web72DPI.jpg?itok=9FPADeDn");
        locationRepository.save(losAngeles);

        User joan = new User("joan", "joan.almeida90@gmail.com", "321pepe");
        joan.getLocations().add(buenosAires);
        userRepository.save(joan);
    }
}
