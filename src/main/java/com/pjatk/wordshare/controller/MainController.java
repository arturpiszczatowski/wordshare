package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.entity.User;
import com.pjatk.wordshare.security.AuthenticationService;
<<<<<<< HEAD
import com.pjatk.wordshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
=======
import com.pjatk.wordshare.service.PoemService;
>>>>>>> b9ce046857446e008828d4da2151d2afacb6ac29
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

<<<<<<< HEAD
import java.security.Principal;
=======
import javax.servlet.http.HttpServletResponse;
>>>>>>> b9ce046857446e008828d4da2151d2afacb6ac29

@Controller
public class MainController {

    AuthenticationService authenticationService;
    PoemService poemService;

    public MainController(AuthenticationService authenticationService, PoemService poemService) {
        this.authenticationService = authenticationService;
        this.poemService = poemService;
    }

    @GetMapping("/")
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
