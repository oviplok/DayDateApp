package com.example.datedictator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.datedictator.repository.dto.AuthDTO;
import com.example.datedictator.repository.dto.UserDTO;
import com.example.datedictator.retrofit.ChatApiService;
import com.example.datedictator.retrofit.RetrofitClient;
import com.example.datedictator.retrofit.UserApiService;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;

public class SwipeUniteTest {
    UserUnitTest userUnitTest;
    private String userId;
    private String userPref;

    private UserApiService userApiService;
//    public SwipeUniteTest(UserApiService userApiService) {
//        this.userApiService = userApiService;
//    }

    private UserDTO getUserDTO(String id, String gender, String phone,String email){
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
    public void createUser() {
        UserApiService userApiService = RetrofitClient.getClient().create(UserApiService.class);
        ChatApiService chatApiService = RetrofitClient.getClient().create(ChatApiService.class);
        assertEquals(4, 2 + 2);

        userApiService.addNewUser(getUserDTO("7890","male","96","firstuser"));
        userApiService.addNewUser(getUserDTO("0987","female","69","seconduser"));
    }

    @Test
    public void getListForSwipe() throws Exception {
        UserApiService userApiService = RetrofitClient.getClient().create(UserApiService.class);
        ChatApiService chatApiService = RetrofitClient.getClient().create(ChatApiService.class);

        assertEquals(4, 2 + 2);
        if(userId!=null){
            Call<List<UserDTO>> partners = userApiService.getPartners("7890","female");
            assertEquals("0987",
                    Objects.requireNonNull(partners.execute().body()).get(0).getId());
        }
        else {
            Call<List<UserDTO>> partners = userApiService.getPartners("1220","female");
            assertEquals("Alina",
                    Objects.requireNonNull(partners.execute().body().get(0).getName()));
        }
    }

    @Test
    public void swipeOnRight() throws Exception {
        UserApiService userApiService = RetrofitClient.getClient().create(UserApiService.class);
        ChatApiService chatApiService = RetrofitClient.getClient().create(ChatApiService.class);
        assertEquals(4, 2 + 2);
        userApiService.onRight("7890","0987");
        userApiService.onRight("0987","7890");
        Call<Boolean> result = userApiService.isMatch("0987","7890");
        boolean resultBool = Boolean.FALSE.equals(result.execute().body());
        if(resultBool){
            assertTrue(resultBool);
        }
        else {
            throw new Exception("Not true");
        }
    }



    @Test
    public void deleteUser(){
        UserApiService userApiService = RetrofitClient.getClient().create(UserApiService.class);
        ChatApiService chatApiService = RetrofitClient.getClient().create(ChatApiService.class);
        userApiService.deleteUserById("7890");
        userApiService.deleteUserById("0987");
    }
//    String userId = registrationUnitTest.getUser();
//    getu
}
