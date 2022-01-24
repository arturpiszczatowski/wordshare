package com.pjatk.wordshare.security;

import com.pjatk.wordshare.entity.User;
import com.pjatk.wordshare.security.Util.UserSession;
import com.pjatk.wordshare.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    final
    UserSession userSession;
    final
    UserService userService;

    public AuthenticationService (UserSession userSession, UserService userService) {
        this.userSession = userSession;
        this.userService = userService;
    }
    //dziala?

    public boolean login(String username, String password){
        if(userService.doesUserExist (username)){
            final User currentUser = userService.findUserByUsername (username);
            final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder ();
            if(encoder.matches(password,currentUser.getPassword ())){
                userSession.logIn();
                User user = new User (currentUser.getUsername(), currentUser.getPassword(), userService.findUserByUsername(username).getAuthority());
                SecurityContextHolder.getContext().setAuthentication(new AppAuthentication (user));
                return true; // Logged in.
            }else{
                return false; // Invalid password.
            }
        }else{
            return false; // User not found.
        }
    }
}
