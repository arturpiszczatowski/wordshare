package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity register(@RequestBody User user) {
        try {
            if(userService.loadUserByUsername(user.getUsername()) == null){
                userService.createOrUpdate(user);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
        }
    }
