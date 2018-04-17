package com.redbee.weatherbee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody User user) {
        User foundUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());

        if(foundUser != null) {
            return new ResponseEntity<>(foundUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(foundUser, HttpStatus.NOT_FOUND);
        }
    }
}
