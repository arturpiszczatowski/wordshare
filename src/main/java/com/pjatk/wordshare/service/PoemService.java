package com.pjatk.wordshare.service;

import com.pjatk.wordshare.entity.Comment;
import com.pjatk.wordshare.entity.Poem;
import com.pjatk.wordshare.entity.User;
import com.pjatk.wordshare.exception.ResourceNotFoundException;
import com.pjatk.wordshare.repository.PoemRepository;
import com.pjatk.wordshare.view.PoemView;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class PoemService {

    private final EntityManager entityManager;
    private final PoemRepository poemRepository;

    public PoemService(EntityManager entityManager, PoemRepository poemRepository) {
        this.entityManager = entityManager;
        this.poemRepository = poemRepository;
    }

    public PoemView view(Long id, HttpServletResponse response){
        Poem poem = entityManager.find(Poem.class, id);
        if(poem == null){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }else{
            HashMap<Long, String> poemComments = new HashMap<>();
            try {
                poemComments = getComments(poem);
            }catch(NoResultException e) {
                e.printStackTrace();
            }
            response.setStatus(HttpStatus.OK.value());
            return new PoemView(poem.getId(), poem.getContent(), poem.getDate(),  poem.getTitle(), poemComments);
        }
    }

    public List<PoemView> viewAll(HttpServletResponse response){
        List<Poem> poemList = poemRepository.findAll();
        List<PoemView> poemViewList = new ArrayList<>();
        if(poemList.isEmpty()){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }else{
            for(Poem poem : poemList) {
                HashMap<Long, String> poemComments = new HashMap<>();
                try {
                    poemComments = getComments(poem);
                } catch (NoResultException e) {
                    e.printStackTrace();
                }
                response.setStatus(HttpStatus.OK.value());
                PoemView newView = new PoemView(poem.getId(), poem.getContent(), poem.getDate(), poem.getTitle(), poemComments);
                poemViewList.add(newView);
            }
        }
        return poemViewList;
    }

    public boolean create(Poem poem, HttpServletResponse response){
        User currentUser = findCurrentUser();
        if(currentUser == null){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }else {
            if (poem.getContent() == null) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                return false;
            }
            if (poem.getTitle() == null) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                return false;
            } else {
                Poem newPoem = new Poem();
                Date currDate = new Date();
                Instant inst = Instant.now();
                newPoem.setRanking(0);
                newPoem.setUser(currentUser);
                newPoem.setDate(currDate.from(inst));
                newPoem.setTitle(poem.getTitle());
                newPoem.setContent(poem.getContent());
                response.setStatus(HttpStatus.CREATED.value());
                entityManager.persist(newPoem);
                return true;
            }
        }
    }

    public Poem edit(Poem poem, HttpServletResponse response, long poemId){
        User currentUser = findCurrentUser();
        Poem existingPoem = entityManager.find(Poem.class, poemId);
        if(existingPoem==null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }else{
            if(currentUser.getId()==existingPoem.getUser().getId()) {
                if(poem.getContent() != null) {
                    existingPoem.setTitle(poem.getTitle());
                    existingPoem.setContent(poem.getContent());
                    Date currDate = new Date();
                    Instant inst = Instant.now();
                    existingPoem.setDate(currDate.from(inst));
                    entityManager.merge(existingPoem);
                    response.setStatus(HttpStatus.OK.value());
                    return existingPoem;
                }else {
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    return null;
                }
            }else{
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return null;
            }
        }
    }

    public void delete(HttpServletResponse response, long poemId){
        User currentUser = findCurrentUser();
        Poem existingPoem = entityManager.find(Poem.class, poemId);
        if(existingPoem==null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }else {
            if (currentUser != null && currentUser.getId() == existingPoem.getUser().getId()) {
                entityManager.remove(existingPoem);
                response.setStatus(HttpStatus.ACCEPTED.value());
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
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

    private User findCurrentUser() {
        return entityManager.createQuery("SELECT user FROM User user WHERE user.username= :username", User.class)
                .setParameter ("username", getCurrentUsername())
                .getSingleResult ();
    }

    public HashMap<Long,String> getComments(Poem poem){
        HashMap<Long,String> comments = new HashMap<>();
        List<Comment> commentValues = entityManager.createQuery("SELECT comments FROM Comment comments WHERE comments.poem.id = :id", Comment.class)
                .setParameter("id", poem.getId())
                .getResultList();
        for(Comment comment : commentValues) {
            comments.put(comment.getId(),comment.getContent());
        }
        return comments;
    }
}