package com.pjatk.wordshare.request;

public class CommentRequest {
    private String content;
    private long poem_id;

    public CommentRequest(String content, long poem_id) {
        this.content = content;
        this.poem_id = poem_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getPoem_id() {
        return poem_id;
    }

    public void setPoem_id(long poem_id) {
        this.poem_id = poem_id;
    }
}
