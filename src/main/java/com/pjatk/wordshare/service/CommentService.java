package com.pjatk.wordshare.service;
import com.pjatk.wordshare.entity.Comment;
import com.pjatk.wordshare.entity.Poem;
import com.pjatk.wordshare.request.CommentRequest;
import com.pjatk.wordshare.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Date;


@Service
public class CommentService {

    private final EntityManager entityManager;

    public CommentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(CommentRequest commentRequest, HttpServletResponse response){
        Comment comment = new Comment();
        User currentUser = findCurrentUser();
        Poem poem = entityManager.find(Poem.class, commentRequest.getPoem_id());
        if(currentUser == null){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }else {
            if(poem == null){
                response.setStatus(HttpStatus.NOT_FOUND.value());
            }else {
                if (commentRequest.getContent() != null) {
                    Date currDate = new Date();
                    Instant inst = Instant.now();
                    comment.setDate(currDate.from(inst));
                    comment.setContent(commentRequest.getContent());
                    comment.setUser(currentUser);
                    comment.setPoem(poem);
                    entityManager.persist(comment);
                    response.setStatus(HttpStatus.CREATED.value());
                } else {
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                }
            }
        }
    }

    public void edit(CommentRequest commentRequest, long commentId, HttpServletResponse response){
        User currentUser = findCurrentUser();
        Comment existingComment = entityManager.find(Comment.class, commentId);
        if(existingComment == null){
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }else {
            if(currentUser == null || currentUser.getId() != existingComment.getUser().getId()){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            } else {
                if(commentRequest.getContent() == null){
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                } else {
                    Date currDate = new Date();
                    Instant inst = Instant.now();
                    existingComment.setDate(currDate.from(inst));
                    existingComment.setContent(commentRequest.getContent());
                    entityManager.merge(existingComment);
                    response.setStatus(HttpStatus.OK.value());
                }
            }
        }
    }

    public void delete(HttpServletResponse response, long commentId) {
        User currentUser = findCurrentUser();
        Comment existingComment = entityManager.find(Comment.class, commentId);
        if(existingComment == null){
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }else{
            if(currentUser == null || currentUser.getId() != existingComment.getUser().getId()){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            } else{
                entityManager.remove(existingComment);
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
}
