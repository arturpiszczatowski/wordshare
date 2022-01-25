package com.pjatk.wordshare.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/home").permitAll ()
                .antMatchers("/login").permitAll ()
                .antMatchers("/register").permitAll ()
                .antMatchers("/profile").hasAuthority("ROLE_USER")
                .antMatchers("/api/poem").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.POST,"/api/poem").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.PUT,"/api/poem").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.DELETE,"/api/poem").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.POST,"/api/comment").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.PUT,"/api/comment").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.DELETE,"/api/comment").hasAuthority("ROLE_USER")
                .and().csrf().disable();
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");
    }
}
