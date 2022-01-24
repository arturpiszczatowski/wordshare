package com.pjatk.wordshare.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers ("/login").permitAll ()
                .antMatchers ("/register").permitAll ()
                .antMatchers ("/profile").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                .antMatchers("/api/*").hasAuthority("ROLE_ADMIN")
                .and().csrf().disable();
    }
}
