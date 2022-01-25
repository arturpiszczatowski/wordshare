package com.pjatk.wordshare.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "poems")
public class Poem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content")
    private String content;

    @Column(name = "title")
    private String title;

    @Column(name = "ranking")
    private int ranking;

    @Column(name = "post_date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User user;

    public Poem (String content) {
        super();
        this.content = content;
    }

    public Poem () {

    }

    public long getId () {
        return id;
    }

    public void setId (long id) {
        this.id = id;
    }

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }

    public int getRanking () {
        return ranking;
    }

    public void setRanking (int ranking) {
        this.ranking = ranking;
    }

    public User getUser () {
        return user;
    }

    public void setUser (User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate () {
        return date;
    }

    public void setDate (Date date) {
        this.date = date;
    }
}
