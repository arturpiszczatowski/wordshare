package com.pjatk.wordshare.service;

import com.pjatk.wordshare.entity.Comment;
import com.pjatk.wordshare.entity.Poem;
import com.pjatk.wordshare.entity.User;
import com.pjatk.wordshare.repository.UserRepository;
import com.pjatk.wordshare.view.PoemView;
import com.pjatk.wordshare.view.ProfileView;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ProfileService {

    private final EntityManager entityManager;
    private final UserRepository userRepository;

    public ProfileService(EntityManager entityManager, UserRepository userRepository) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
    }

    public List<ProfileView> viewAll(HttpServletResponse response){
        List<User> userList = userRepository.findAll();
        List<ProfileView> profileViewsList = new ArrayList<>();
        if(userList.isEmpty()){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }else{
            for(User user : userList) {
                HashMap<Long, String> poemTitles = new HashMap<>();
                try {
                    poemTitles = getPoemsTitles(user);
                } catch (NoResultException e) {
                    e.printStackTrace();
                }
                response.setStatus(HttpStatus.OK.value());
                ProfileView newView = new ProfileView(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), poemTitles);
                profileViewsList.add(newView);
            }
        }
        return profileViewsList;
    }

    public ProfileView view(Long id, HttpServletResponse response){
        User user = entityManager.find(User.class, id);
        if(user == null){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }else{
            HashMap<Long, String> poemTitles = new HashMap<>();
            try {
                poemTitles = getPoemsTitles(user);
            }catch(NoResultException e) {
                e.printStackTrace();
            }
            response.setStatus(HttpStatus.OK.value());
            return new ProfileView(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), poemTitles);
        }
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

    public HashMap<Long,String> getPoemsTitles(User user){
        HashMap<Long,String> poems = new HashMap<>();
        List<Poem> poemValues = entityManager.createQuery("SELECT poems FROM Poem poems WHERE poems.user.id = :id", Poem.class)
                .setParameter("id", user.getId())
                .getResultList();
        for(Poem poem : poemValues) {
            poems.put(poem.getId(),poem.getTitle());
        }
        return poems;
    }
}
