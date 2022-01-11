package com.pjatk.wordshare.entity;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "poem_id")
    private Poem poem;

    public Comment (Long id, String content, User user, Poem poem) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.poem = poem;
    }

    public Comment () {

    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }

    public User getUser () {
        return user;
    }

    public void setUser (User user) {
        this.user = user;
    }

    public Poem getPoem () {
        return poem;
    }

    public void setPoem (Poem poem) {
        this.poem = poem;
    }

}
