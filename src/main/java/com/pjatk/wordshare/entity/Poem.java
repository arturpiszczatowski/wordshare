package com.pjatk.wordshare.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "poems")
public class Poem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "ranking")
    private int ranking;

    @Column(name = "date")
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


    public Date getDate () {
        return date;
    }

    public void setDate (Date date) {
        this.date = date;
    }
}
