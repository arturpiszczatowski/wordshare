package com.pjatk.wordshare.view;

import java.util.Date;
import java.util.HashMap;

public class PoemView {
    private Long id;
    private String content;
    private Date date;
    private HashMap<Long, String> comments;

    public PoemView(Long id, String content, Date date, HashMap<Long, String> comments) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.comments = comments;
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
