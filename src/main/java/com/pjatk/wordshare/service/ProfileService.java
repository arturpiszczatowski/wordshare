package com.pjatk.wordshare.service;

import com.pjatk.wordshare.entity.User;
import com.pjatk.wordshare.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class ProfileService {

    private final EntityManager entityManager;
    private final UserRepository userRepository;

    public ProfileService(EntityManager entityManager, UserRepository userRepository) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
    }



    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof User) {
            String username = ((User) principal).getUsername();
            return username;
        }else {
            return principal.toString();
        }
    }

    public User findCurrentUser() {
        return entityManager.createQuery("SELECT user FROM User user WHERE user.username= :username", User.class)
                .setParameter ("username", getCurrentUsername())
                .getSingleResult ();
    }
}
