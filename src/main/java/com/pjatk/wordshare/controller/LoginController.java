package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.exception.UnauthorizedException;
import com.pjatk.wordshare.login.LoginRequest;
import com.pjatk.wordshare.security.AuthenticationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService){
        this.authenticationService=authenticationService;
    }


    @PostMapping()
    public void login(LoginRequest loginRequest){
        var isLogged = authenticationService.login(loginRequest.getUsername(),loginRequest.getPassword());
        if(!isLogged){
            throw new UnauthorizedException();
        }
    }
}