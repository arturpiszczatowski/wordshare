package com.pjatk.wordshare.request;

public class CommentRequest {
    private String content;
    private Long poem_id;

    public CommentRequest(String content, Long poem_id) {
        this.content = content;
        this.poem_id = poem_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPoem_id() {
        return poem_id;
    }

    public void setPoem_id(Long poem_id) {
        this.poem_id = poem_id;
    }
}
