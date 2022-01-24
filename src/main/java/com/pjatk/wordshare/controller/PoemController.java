package com.pjatk.wordshare.controller;

import com.pjatk.wordshare.entity.Poem;
import com.pjatk.wordshare.repository.PoemRepository;
import com.pjatk.wordshare.service.PoemService;
import com.pjatk.wordshare.view.PoemView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/poems")
public class PoemController {

    @Autowired
    private final PoemRepository poemRepository;

    private final PoemService poemService;

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
        return poemService.guwno(poemId, response); ///viewPoem
    }

    // create poem
    @PostMapping
    @Transactional
    public void postPoem(Poem poem, HttpServletResponse response){
        poemService.create(poem, response);
    }

    // update poem
    @PutMapping("/{id}")
    @Transactional
    public Poem putPoem(Poem poem, @PathVariable("id") long poemId, HttpServletResponse response){
        return poemService.edit(poem, response, poemId);
    }

    // delete poem by id
    @DeleteMapping("/{id}")
    @Transactional
    public void removePoem(HttpServletResponse response, @PathVariable (value = "id" ) long poemId){
        poemService.delete(response, poemId);
    }
}
