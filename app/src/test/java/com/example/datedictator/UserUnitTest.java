package com.example.datedictator;

import static org.junit.Assert.assertEquals;

import com.example.datedictator.repository.dto.AuthDTO;
import com.example.datedictator.repository.dto.UserDTO;
import com.example.datedictator.retrofit.UserApiService;

import org.junit.Test;

import java.util.List;

import retrofit2.Call;

public class UserUnitTest {
    private String userId;
    private String userPref;

    private UserApiService userApiService;

    public UserUnitTest(UserApiService userApiService) {
        this.userApiService = userApiService;
    }

    @Test
    public void createUser() {
        userApiService.addNewUser(getUserDTO());
    }

    @Test
    public void getUser() throws Exception {

        AuthDTO auth = new AuthDTO();
        auth.setMail("androidtest@mail.ru");
        auth.setPassword("TestPassword");
        auth.setPhone("+79995556612");
        String userId = userApiService.userAuth(auth).toString();
        UserDTO userDTO = (UserDTO) userApiService.getUserById(userId);
        assertEquals("TestUser", userDTO.getName());
        this.userId = userDTO.getId();
        userPref=userDTO.getSex();
        if(userPref=="male"){
            userPref="female";
        } else if (userPref=="female") {
            userPref="male";
        }
        else {
            throw new Exception("no prefers set");

        }
    }

    @Test
    public void getListForSwipe() throws Exception {
        if(userId!=null){
          Call< List<UserDTO>> partners = userApiService.getPartners(userId,userPref);

        }
        else {
            throw new Exception("ID is null");
        }
    }


    @Test
    public void deleteUser(){
        AuthDTO auth = new AuthDTO();
        auth.setMail("androidtest@mail.ru");
        auth.setPassword("TestPassword");
        auth.setPhone("+79995556612");
        String userId = userApiService.userAuth(auth).toString();
        userApiService.deleteUserById(userId);
    }
    private UserDTO getUserDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setProfileImageUrl("default");
        userDTO.setSex("male");
        userDTO.setPhone("+79995556612");
        userDTO.setName("TestUser");
        userDTO.setMail("androidtest@mail.ru");
        userDTO.setPassword("TestPassword");
        userDTO.setId("2134");
       return userDTO;

    }
}
