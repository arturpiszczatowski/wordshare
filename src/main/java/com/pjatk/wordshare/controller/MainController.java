package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.entity.User;
import com.pjatk.wordshare.security.AuthenticationService;
import com.pjatk.wordshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {

    AuthenticationService authenticationService;

    public MainController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        if(authenticationService.isAuthenticated()){
            return "home";
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model){
        if(authenticationService.isAuthenticated()){
            return "home";
        }
        return "register";
    }
}
