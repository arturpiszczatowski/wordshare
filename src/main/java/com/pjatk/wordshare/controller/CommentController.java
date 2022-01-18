package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.entity.Comment;
import com.pjatk.wordshare.request.CommentRequest;
import com.pjatk.wordshare.exception.ResourceNotFoundException;
import com.pjatk.wordshare.repository.CommentRepository;
import com.pjatk.wordshare.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    private CommentService commentService;

    public CommentController(CommentRepository commentRepository, CommentService commentService) {
        this.commentRepository = commentRepository;
        this.commentService = commentService;
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
    @PostMapping
    public void createComment(@RequestBody CommentRequest commentRequest, HttpServletResponse response){
        commentService.createComment(commentRequest, response);
    }

    // update comment
    @PutMapping("/{id}")
    public void updateComment(@RequestBody CommentRequest commentRequest, @PathVariable("id") long commentId, HttpServletResponse response){
        commentService.editComment(commentRequest, commentId, response);
    }

    // delete comment by id
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable (value = "id" ) long commentId, HttpServletResponse response){
        commentService.deleteComment(response, commentId);
    }
}

