package com.pjatk.wordshare.view;

import com.pjatk.wordshare.entity.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileView {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private HashMap<Long, String> poemTitles;

    public ProfileView(Long id, String firstName, String lastName, String email, String username, HashMap<Long,String> poemTitles){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.poemTitles = poemTitles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public HashMap<Long, String> getPoemTitles() {
        return poemTitles;
    }

    public void setPoemTitles(HashMap<Long, String> poemTitles) {
        this.poemTitles = poemTitles;
    }

    public String getRecentTitle(){
        HashMap<Long,String> poems = getPoemTitles();
        ArrayList<String> titles = new ArrayList<>();
        for(String title : poems.values()){
            titles.add(title);
        }

        return titles.get(titles.size()-1);
    }
}
