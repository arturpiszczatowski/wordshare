package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.entity.Poem;
import com.pjatk.wordshare.entity.User;
import com.pjatk.wordshare.exception.ResourceNotFoundException;
import com.pjatk.wordshare.repository.PoemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/poems")
public class PoemController {

    @Autowired
    private PoemRepository poemRepository;

    // get all poems
    @GetMapping
    public List<Poem> getAllPoems(){
        return this.poemRepository.findAll();
    }

    // get poem by id
    @GetMapping("/{id}")
    public Poem getPoemById(@PathVariable(value = "id" ) long poemId){
        return this.poemRepository.findById(poemId)
                .orElseThrow(() -> new ResourceNotFoundException ("Poem not found with id :" + poemId));
    }

    // create poem
    @PostMapping
    public Poem createPoem(@RequestBody Poem poem){
        Date currDate = new Date ();
        Instant inst = Instant.now ();
        poem.setRanking (0);
        // poem.setUser (user);
        poem.setDate(currDate.from(inst));
        return this.poemRepository.save(poem);
    }

    // update poem
    @PutMapping("/{id}")
    public Poem updatePoem(@RequestBody Poem poem, @PathVariable("id") long poemId){
        Poem existingPoem = this.poemRepository.findById(poemId)
                .orElseThrow(() -> new ResourceNotFoundException("Poem not found with id :" + poemId));
        existingPoem.setContent (poem.getContent());
        return this.poemRepository.save(existingPoem);
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
