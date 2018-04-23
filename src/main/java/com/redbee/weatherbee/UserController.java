package com.redbee.weatherbee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/api/user/{id}/location")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Set<Location>> getUsersLocations(@PathVariable("id") Long userId) {
        User foundUser = userRepository.findById(userId);
        //Set<Location> locations = new HashSet<>();
        Set<Location> locations = new HashSet<>();
        if (foundUser != null) {
            locations = foundUser.getLocations();
            return new ResponseEntity<>(locations, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(locations, HttpStatus.NOT_FOUND);
        }
    }

    //TODO: Should this be a put? Figure it out later
    //TODO: No agregar repetidas
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Set<Location>> addUsersLocation(@PathVariable("id") Long userId, @RequestBody Location location) {
        User foundUser = userRepository.findById(userId);
        Location existentLocation = locationRepository.findByName(location.getName());
        if (existentLocation != null) {
            //Si ya la tengo agregada
            Set<Location> userLocations = foundUser.getLocations();
            if (userLocations.contains(existentLocation) ) {
                return new ResponseEntity<>(foundUser.getLocations(), HttpStatus.CONFLICT);
            } else {
                userLocations.add(existentLocation);
                userRepository.save(foundUser);
                return new ResponseEntity<>(userLocations, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Set<Location>> deleteUsersLocation(@PathVariable("id") Long userId, @RequestBody Location location) {
        User foundUser = userRepository.findById(userId);
        Location existentLocation = locationRepository.findByName(location.getName());
        Set<Location> locations = foundUser.getLocations();
        if (existentLocation != null) {
            locations.remove(existentLocation);
            foundUser.setLocations(locations);
            userRepository.save(foundUser);
            return new ResponseEntity<>(locations, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(locations, HttpStatus.NOT_FOUND);
        }
    }
}
