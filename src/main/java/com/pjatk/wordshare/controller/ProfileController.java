package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.repository.UserRepository;
import com.pjatk.wordshare.service.ProfileService;
import com.pjatk.wordshare.view.ProfileView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private final UserRepository userRepository;

    private final ProfileService profileService;


    public ProfileController(ProfileService profileService, UserRepository userRepository){
        this.userRepository=userRepository;
        this.profileService=profileService;
    }

    @GetMapping
    public List<ProfileView> getAllProfiles(HttpServletResponse response){
        return profileService.viewAll(response);
    }


    @GetMapping("/{id}")
    public ProfileView getProfileById(@PathVariable(value = "id") long profileId, HttpServletResponse response){
        return profileService.view(profileId, response);
    }


//    @GetMapping()
//    public String profilePage(Model model){
//        if(authenticationService.isAuthenticated()){
//            User user = profileService.findCurrentUser();
//            model.addAttribute("username", user.getUsername());
//            model.addAttribute("firstname", user.getFirstName());
//            model.addAttribute("lastname", user.getLastName());
//            model.addAttribute("email", user.getEmail());
//            return "profile";
//        }
//        return "login";
//    }
}
