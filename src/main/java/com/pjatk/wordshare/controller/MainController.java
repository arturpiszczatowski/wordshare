package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.entity.User;
import com.pjatk.wordshare.security.AuthenticationService;
import com.pjatk.wordshare.service.PoemService;
import com.pjatk.wordshare.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {

    AuthenticationService authenticationService;
    PoemService poemService;
    ProfileService profileService;

    public MainController(AuthenticationService authenticationService, PoemService poemService, ProfileService profileService) {
        this.authenticationService = authenticationService;
        this.poemService = poemService;
        this.profileService = profileService;
    }

    @GetMapping("/home")
    public String homePage(Model model, HttpServletResponse response) {
        model.addAttribute("poems", poemService.viewAll(response));
        return "home";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        if(authenticationService.isAuthenticated()){
            return "home";
        }

        return "login";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, HttpServletResponse response){
        if(authenticationService.isAuthenticated()){
            User user = profileService.findCurrentUser();
            Long userId = user.getId();
            model.addAttribute("profile", profileService.view(userId, response));
            return "profile";
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
    @GetMapping("/add_poem")
    public String addPoem(Model model, HttpServletResponse response){
        if(!authenticationService.isAuthenticated()){
            response.setStatus (HttpStatus.UNAUTHORIZED.value ());
            model.addAttribute("notLogged","notLogged");
            return "login";
        }
        return "add_poem";
    }
}
