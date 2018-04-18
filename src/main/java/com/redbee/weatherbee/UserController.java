package com.redbee.weatherbee;

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
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin
    @RequestMapping(value = "/api/user/{id}/locations", method = RequestMethod.GET)
    public ResponseEntity<Set<Location>> getUserLocations(@PathVariable("id") Long id) {
        User foundUser = userRepository.findById(id);
        Set<Location> locations = new HashSet<>();
        if (foundUser != null) {
            locations = foundUser.getLocations();
            return new ResponseEntity<>(locations, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(locations, HttpStatus.NOT_FOUND);
        }
    }
}
