package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.entity.User;
import com.pjatk.wordshare.repository.UserRepository;
import com.pjatk.wordshare.security.AuthenticationService;
import com.pjatk.wordshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    UserService userService;


    private final AuthenticationService authenticationService;

    public ProfileController(AuthenticationService authenticationService){
        this.authenticationService=authenticationService;
    }

    @GetMapping()
    public String profilePage(Model model){
        if(authenticationService.isAuthenticated()){
            User user = userService.findCurrentUser();
            model.addAttribute("username", user.getUsername());
            model.addAttribute("firstname", user.getFirstName());
            model.addAttribute("lastname", user.getLastName());
            model.addAttribute("email", user.getEmail());
            return "profile";
        }
        return "register";
    }
}
