package com.pjatk.wordshare.view;

import java.util.Date;
import java.util.HashMap;

public class PoemView {
    private Long id;
    private String content;
    private Date date;
    private String title;
    private HashMap<Long, String> comments;

    public PoemView(Long id, String content, Date date, String title, HashMap<Long, String> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HashMap<Long, String> getComments() {
        return comments;
    }

    public void setComments(HashMap<Long, String> comments) {
        this.comments = comments;
    }
}
