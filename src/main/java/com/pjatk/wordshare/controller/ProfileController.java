package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.entity.User;
import com.pjatk.wordshare.repository.UserRepository;
import com.pjatk.wordshare.security.AuthenticationService;
import com.pjatk.wordshare.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @Autowired
    UserRepository userRepository;

    private final AuthenticationService authenticationService;

    public ProfileController(AuthenticationService authenticationService, ProfileService profileService, UserRepository userRepository){
        this.authenticationService=authenticationService;
        this.profileService=profileService;
        this.userRepository=userRepository;
    }

    @GetMapping()
    public String profilePage(Model model){
        if(authenticationService.isAuthenticated()){
            User user = profileService.findCurrentUser();
            model.addAttribute("username", user.getUsername());
            model.addAttribute("firstname", user.getFirstName());
            model.addAttribute("lastname", user.getLastName());
            model.addAttribute("email", user.getEmail());
            return "profile";
        }
        return "login";
    }
}
