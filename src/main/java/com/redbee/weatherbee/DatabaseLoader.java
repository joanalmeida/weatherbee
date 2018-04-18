package com.redbee.weatherbee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner{
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    @Autowired
    public DatabaseLoader(LocationRepository repo, UserRepository userRepo) {
        locationRepository = repo;
        userRepository = userRepo;
    }

    @Override
    public void run(String... strings) throws Exception {


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
    }
}
