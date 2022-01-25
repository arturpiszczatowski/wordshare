package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.entity.Comment;
import com.pjatk.wordshare.exception.ResourceNotFoundException;
import com.pjatk.wordshare.repository.CommentRepository;
import com.pjatk.wordshare.request.CommentRequest;
import com.pjatk.wordshare.security.AuthenticationService;
import com.pjatk.wordshare.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    private CommentService commentService;

    AuthenticationService authenticationService;

    public CommentController(CommentRepository commentRepository, CommentService commentService, AuthenticationService authenticationService) {
        this.commentRepository = commentRepository;
        this.commentService = commentService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public List<Comment> getAllComments(){
        return this.commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable (value = "id") Long commentId){
        return this.commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id :" + commentId));
    }

    // create comment
    @Transactional
    @PostMapping("/?{id}")
    public String createComment(@PathVariable (value = "id") long poemId, CommentRequest commentRequest, HttpServletResponse response, Model model){
        if(!authenticationService.isAuthenticated()){
            response.setStatus(HttpStatus.UNAUTHORIZED.value ());
            model.addAttribute("commentNotLogged","commentNotLogged");
            return "login";
        }
        commentRequest.setPoem_id(poemId);
        commentService.create(commentRequest, response);
        return "home";
    }

    // update comment
    @PutMapping("/{id}")
    @Transactional
    public void updateComment(CommentRequest commentRequest, @PathVariable("id") long commentId, HttpServletResponse response){
        commentService.edit(commentRequest, commentId, response);
    }

    // delete comment by id
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteUser(@PathVariable (value = "id" ) long commentId, HttpServletResponse response){
        commentService.delete(response, commentId);
    }
}

