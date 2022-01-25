package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.entity.User;
import com.pjatk.wordshare.register.RegisterRequest;
import com.pjatk.wordshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;


    @PostMapping
    @Transactional
    public String register(RegisterRequest registerRequest, HttpServletResponse response, Model model){

        if(userService.doesUserExist (registerRequest.getUsername ())){
            response.setStatus (HttpStatus.CONFLICT.value ()); //User already exists.
            return "register";
        }else {
            if(registerRequest.getPassword ()==null || registerRequest.getPassword ()==""){
                response.setStatus (HttpStatus.CONFLICT.value ()); //Invalid password.
                return "register";
            }else {
                User newUser = new User(registerRequest.getFirst_name(), registerRequest.getLast_name(), registerRequest.getEmail() ,registerRequest.getUsername(),registerRequest.getPassword()); //New user created.
                if(userService.isEmpty ()){
                    GrantedAuthority adminAuthority = () -> "ROLE_ADMIN";
                    newUser.addAuthority(adminAuthority);
                }
                GrantedAuthority defaultAuthority = () -> "ROLE_USER";
                newUser.addAuthority(defaultAuthority);
                userService.saveUser(newUser);
                model.addAttribute("registered","registered");
                return "login";
            }
        }
    }
}