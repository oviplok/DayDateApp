package com.example.datedictator;

import com.example.datedictator.repository.dto.ChatDTO;
import com.example.datedictator.repository.dto.MessageDTO;
import com.example.datedictator.repository.dto.UserDTO;
import com.example.datedictator.retrofit.ChatApiService;
import com.example.datedictator.retrofit.UserApiService;

import org.junit.Test;

import retrofit2.Call;

public class ChatUnitTest {
    private UserApiService userApiService;
    private ChatApiService chatApiService;
    public ChatUnitTest(UserApiService userApiService,ChatApiService chatApiService) {
        this.userApiService = userApiService;
        this.chatApiService = chatApiService;
    }

    private UserDTO getUserDTO(String id, String gender, String phone, String email){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setProfileImageUrl("default");
        userDTO.setSex(gender);
        userDTO.setPhone("+799955566"+phone);
        userDTO.setName("TestUser");
        userDTO.setMail(email+"@mail.ru");
        userDTO.setPassword("TestPassword");
        return userDTO;
    }

    @Test
    public void createUsers() throws Exception {
        userApiService.addNewUser(getUserDTO("0110","male","01","firstchatter"));
        userApiService.addNewUser(getUserDTO("1001","female","10","secondchatter"));

        userApiService.onRight("0110","1001");
        userApiService.onRight("1001","0110");
        Call<Boolean> result = userApiService.isMatch("0987","7890");
        boolean resultBool = Boolean.FALSE.equals(result.execute().body());
        if(resultBool){

        }
        else {
            throw new Exception("Not true");
        }
    }

    @Test
    public void sendMessage() throws Exception {
        ChatDTO chat = chatApiService.findChatByUsers("0110","1001");
        if(chat!=null){
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setChatId(chat.getId());
            messageDTO.setMessageText("Hello!");
            messageDTO.setRead(false);
            messageDTO.setUserId("0110");
            chatApiService.sendMessage(chat.getId(),messageDTO);
        }
        else {
            throw new Exception("Not exist");
        }
    }



}
