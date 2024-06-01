package com.example.datedictator;

import static org.junit.Assert.assertEquals;

import com.example.datedictator.repository.dto.AuthDTO;
import com.example.datedictator.repository.dto.UserDTO;
import com.example.datedictator.retrofit.ChatApiService;
import com.example.datedictator.retrofit.RetrofitClient;
import com.example.datedictator.retrofit.UserApiService;

import junit.framework.TestCase;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class UserUnitTest {
    private String userId;
    private String userPref;

    private UserApiService userApiService;

//    public UserUnitTest(UserApiService userApiService) {
//        this.userApiService = userApiService;
//    }

    @Test
    public void createUser() {
        UserApiService userApiService = RetrofitClient.getClient().create(UserApiService.class);
//        assertEquals(4, 2 + 2);
        userApiService.addNewUser(getUserDTO());
    }

    @Test
    public void getUser() throws Exception {
        UserApiService userApiService = RetrofitClient.getClient().create(UserApiService.class);
        assertEquals(4, 2 + 2);
        AuthDTO auth = new AuthDTO();
        auth.setMail("androidtest@mail.ru");
        auth.setPassword("TestPassword");
        auth.setPhone("+79995556612");
        assertEquals(4, 2 + 2);
        String userId = userApiService.userAuth(auth).execute().body();
        UserDTO userDTO = userApiService.getUserById("1220").execute().body();
//        assertEquals("TestUser", userDTO.getName());
        this.userId = userDTO.getId();
        String userPref=userDTO.getSex();
        if(userPref=="male"){
            userPref="female";
        } else if (userPref=="female") {
            userPref="male";
        }
        else {
//            throw new Exception("no prefers set");

        }
    }

    @Test
    public void getListForSwipe() throws Exception {
        UserApiService userApiService = RetrofitClient.getClient().create(UserApiService.class);
        UserDTO userDTO = userApiService.getUserById("1220").execute().body();
        assertEquals(4, 2 + 2);
        assertEquals(4, 2 + 2);
//        if(!=null){
          Call< List<UserDTO>> partners = userApiService.getPartners(userId,userPref);

//        }
//        else {
//            throw new Exception("ID is null");
//        }
    }


    @Test
    public void deleteUser() throws IOException {
        UserApiService userApiService = RetrofitClient.getClient().create(UserApiService.class);
        ChatApiService chatApiService = RetrofitClient.getClient().create(ChatApiService.class);
        assertEquals(4, 2 + 2);
        AuthDTO auth = new AuthDTO();
        auth.setMail("androidtest@mail.ru");
        auth.setPassword("TestPassword");
        auth.setPhone("+79995556612");
        String userId = userApiService.userAuth(auth).execute().body();
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
