package com.example.datedictator.repository.dto;

import java.util.List;

public class ChatDTO {

    private String id;

    private List<MessageDTO> messages;

    private List<String> usersID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }

    public List<String> getUsersID() {
        return usersID;
    }

    public void setUsersID(List<String> usersID) {
        this.usersID = usersID;
    }
}
