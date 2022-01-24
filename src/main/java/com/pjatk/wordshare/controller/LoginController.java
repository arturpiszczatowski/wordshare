package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.exception.UnauthorizedException;
import com.pjatk.wordshare.login.LoginRequest;
import com.pjatk.wordshare.security.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService){
        this.authenticationService=authenticationService;
    }


    @PostMapping()
    public String login(LoginRequest loginRequest , HttpServletResponse response, Model model){
        var isLogged = authenticationService.login(loginRequest.getUsername(),loginRequest.getPassword());
        if(!isLogged){
            response.setStatus (HttpStatus.UNAUTHORIZED.value ());
            model.addAttribute("logError","logError");
            return "login";
        }
        return "home";
    }
}