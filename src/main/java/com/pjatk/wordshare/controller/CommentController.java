package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.entity.Comment;
import com.pjatk.wordshare.exception.ResourceNotFoundException;
import com.pjatk.wordshare.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    public List<Comment> getAllComments(){
        return this.commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable (value = "id") Long commentId){
        return this.commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id :" + commentId));
    }

    // create user
    @PostMapping
    public Comment createComment(@RequestBody Comment comment){
        return this.commentRepository.save(comment);
    }

    // update user
    @PutMapping("/{id}")
    public Comment updateComment(@RequestBody Comment comment, @PathVariable("id") long commentId){
        Comment existingComment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id :" + commentId));
        existingComment.setContent(comment.getContent());
        existingComment.setDate(comment.getDate());
        return this.commentRepository.save(existingComment);
    }

    // delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteUser(@PathVariable (value = "id" ) long commentId){
        Comment existingComment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id :" + commentId));
        this.commentRepository.delete(existingComment);
        return ResponseEntity.ok().build();
    }
}
