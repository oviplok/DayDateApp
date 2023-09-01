package com.example.datedictator.repository.model;

public class Card {
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String userId, name;
    public Card(String userId, String name){
        this.userId = userId;
        this.name = name;

    }
}
