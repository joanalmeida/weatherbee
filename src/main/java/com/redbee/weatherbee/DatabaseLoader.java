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
        locationRepository.save(new Location("Buenos Aires"));
        locationRepository.save(new Location("New York"));

        userRepository.save(new User("joan", "joan.almeida90@gmail.com", "321pepe"));
    }
}
