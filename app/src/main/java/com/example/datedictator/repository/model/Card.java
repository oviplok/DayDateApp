package com.example.datedictator.repository.model;

import com.example.datedictator.repository.dto.UserDTO;

public class Card {

    private String userId;
    private String name;
    private String profileImageUrl;

    public void setDataFromDTO(UserDTO userDTO){
        this.userId = userDTO.getId();
        this.name = userDTO.getName();
        this.profileImageUrl = userDTO.getProfileImageUrl();
    }

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
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Card(String userId, String name, String imageUrl){
        this.userId = userId;
        this.name = name;
        this.profileImageUrl = imageUrl;
    }
}
