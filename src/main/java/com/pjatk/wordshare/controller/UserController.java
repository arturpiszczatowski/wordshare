package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.entity.User;
import com.pjatk.wordshare.exception.ResourceNotFoundException;
import com.pjatk.wordshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder ();

    @Autowired
    private UserRepository userRepository;

    // get all users
    @GetMapping
    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    // get user by id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable (value = "id" ) long userId){
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
    }

    // create user
    @PostMapping
    public User createUser(User user){
        User newUser = new User ();
        newUser.setEmail (user.getEmail ());
        newUser.setFirstName (user.getFirstName ());
        newUser.setLastName (user.getLastName ());
        newUser.setUsername (user.getUsername ());
        newUser.setPassword (passwordEncoder.encode(user.getPassword ()));
        return this.userRepository.save(newUser);
    }

    // update user
    @PutMapping("/{id}")
    public User updateUser(User user, @PathVariable("id") long userId){
        User existingUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
        if(user.getFirstName () != null) existingUser.setFirstName(user.getFirstName());
        if(user.getLastName () != null) existingUser.setLastName(user.getLastName());
        if(user.getEmail () != null) existingUser.setEmail (user.getEmail ());
        if(user.getPassword () != null) existingUser.setPassword (passwordEncoder.encode(user.getPassword ()));
        return this.userRepository.save(existingUser);
    }

    // delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable (value = "id" ) long userId){
        User existingUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
        this.userRepository.delete(existingUser);
        return ResponseEntity.ok().build();
    }
}
