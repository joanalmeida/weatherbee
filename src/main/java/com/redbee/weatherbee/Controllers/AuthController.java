package com.redbee.weatherbee.Controllers;

import com.redbee.weatherbee.Entities.User;
import com.redbee.weatherbee.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody User user) {
        User foundUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());

        if(foundUser != null) {
            return new ResponseEntity<>(foundUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(foundUser, HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> register(@RequestBody User user) {
        User foundUser = userRepository.findByEmail(user.getEmail());

        if(foundUser != null) {
            return new ResponseEntity<>(user, HttpStatus.CONFLICT);
        } else {
            User newUser = new User(user.getName(), user.getEmail(), user.getPassword());
            newUser = userRepository.save(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        }
    }
}
