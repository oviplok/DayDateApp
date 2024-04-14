package com.example.datedictator.repository.dto;

import java.time.LocalDateTime;

public class MessageDTO {

    String id;
    String chatId;
    String userId;
    String messageText;
    boolean isRead;
    private LocalDateTime messageTime;


    public void setMessageTime(LocalDateTime messageTime) {
        this.messageTime = messageTime;
    }
}
