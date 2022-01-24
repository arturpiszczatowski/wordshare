package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.entity.User;
import com.pjatk.wordshare.security.AuthenticationService;
import com.pjatk.wordshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    private final AuthenticationService authenticationService;

    public ProfileController(AuthenticationService authenticationService){
        this.authenticationService=authenticationService;
    }

    @GetMapping()
    public String profilePage(Model model, @AuthenticationPrincipal User user){
        if(authenticationService.isAuthenticated()){
            model.addAttribute("user", user);
            return "profile";
        }
        return "register";
    }
}
