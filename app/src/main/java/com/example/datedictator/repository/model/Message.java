package com.example.datedictator.repository.model;

public class Message {
    private String text;
    private Boolean currentUser;
    public Message(String text, Boolean currentUser){
        this.text = text;
        this.currentUser = currentUser;
    }

    public String getText(){
        return text;
    }
    public void setText(String userID){
        this.text = text;
    }

    public Boolean getCurrentUser(){
        return currentUser;
    }
    public void setCurrentUser(Boolean currentUser){
        this.currentUser = currentUser;
    }
}
