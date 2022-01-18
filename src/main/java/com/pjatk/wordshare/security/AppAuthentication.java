package com.pjatk.wordshare.security;

import com.pjatk.wordshare.entity.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AppAuthentication extends AbstractAuthenticationToken {
    private final User authenticatedUser;

    public AppAuthentication(User authenticatedUser){
        super(authenticatedUser.getAuthorities());
        this.authenticatedUser=authenticatedUser;
        setAuthenticated (true);
    }

    @Override
    public Object getCredentials(){
        return authenticatedUser.getPassword ();
    }
    @Override
    public Object getPrincipal(){
        return authenticatedUser;
    }
}
