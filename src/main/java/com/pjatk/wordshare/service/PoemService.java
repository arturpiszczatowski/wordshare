package com.pjatk.wordshare.service;

import com.pjatk.wordshare.entity.Comment;
import com.pjatk.wordshare.entity.Poem;
import com.pjatk.wordshare.view.PoemView;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Service
public class PoemService {

    private final EntityManager entityManager;
    private final CommentService commentService;

    public PoemService(EntityManager entityManager, CommentService commentService) {
        this.entityManager = entityManager;
        this.commentService = commentService;
    }

    public PoemView viewPoem(Long id, HttpServletResponse response){
        Poem poem = entityManager.find(Poem.class, id);
        if(poem==null){
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
            return new PoemView(poem.getId(), poem.getContent(), poem.getDate(), poemComments);
        }
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