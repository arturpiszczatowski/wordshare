package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.entity.Poem;
import com.pjatk.wordshare.entity.User;
import com.pjatk.wordshare.exception.ResourceNotFoundException;
import com.pjatk.wordshare.repository.PoemRepository;
import com.pjatk.wordshare.service.PoemService;
import com.pjatk.wordshare.view.PoemView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/poems")
public class PoemController {

    @Autowired
    private PoemRepository poemRepository;

    PoemService poemService;

    public PoemController(PoemRepository poemRepository, PoemService poemService) {
        this.poemRepository = poemRepository;
        this.poemService = poemService;
    }

    // get all poems
    @GetMapping
    public List<Poem> getAllPoems(){
        return this.poemRepository.findAll();
    }

    // get poem by id
    @GetMapping("/{id}")
    public PoemView getPoemById(@PathVariable(value = "id" ) long poemId, HttpServletResponse response){
        return poemService.viewPoem(poemId, response);
    }

    // create poem
    @PostMapping
    public void createPoem(@RequestBody Poem poem, HttpServletResponse response){
        poemService.createPoem(poem, response);
    }

    // update poem
    @PutMapping("/{id}")
    public Poem updatePoem(@RequestBody Poem poem, @PathVariable("id") long poemId, HttpServletResponse response){
        return poemService.editPoem(poem, response, poemId);
    }

    // delete poem by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Poem> deletePoem(@PathVariable (value = "id" ) long poemId){
        Poem existingPoem = this.poemRepository.findById(poemId)
                .orElseThrow(() -> new ResourceNotFoundException("Poem not found with id :" + poemId));
        this.poemRepository.delete(existingPoem);
        return ResponseEntity.ok().build();
    }
}
