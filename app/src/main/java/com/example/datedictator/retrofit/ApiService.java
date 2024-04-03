package com.example.datedictator.retrofit;

import com.example.datedictator.repository.dto.AuthDTO;
import com.example.datedictator.repository.dto.UserDTO;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("user/{usr_id}")
    Call<UserDTO> getUserById(@Path("usr_id") String userID);

    @POST("user/add-profile")
    void addNewUser(@Body UserDTO userDTO);

    @DELETE("user/{usr_id}/delete-profile")
    void deleteUserById(@Path("usr_id") String userID);

    @PUT("user/{usr_id}/update-profile")
    void updateUserProfile(@Path("usr_id") String userID);

    @GET("user/{usr_id}/get_sex")
    Call<String> getUserSex(@Path("usr_id") String userID);

    @GET("user/{usr_id}/partners/{pref_sex}")
    Call<List<UserDTO>> getPartners(@Path("usr_id") String userID,
                                    @Path("pref_sex") String pref_sex);

    @PUT("user/{usr_id}/connections/right/{usr_id2}")
    void onRight(@Path("usr_id") String userID,
                                    @Path("usr_id2") String partnerID);

    @PUT("user/{usr_id}/connections/left/{usr_id2}")
    void onLeft(@Path("usr_id") String userID,
                         @Path("usr_id2") String partnerID);
    @PUT("user/{usr_id}/connections/matches/is_match/{usr_id2}")
    Call<Boolean> isMatch(@Path("usr_id") String userID,
                          @Path("usr_id2") String partnerID);

    @GET("user/{usr_id}/connections/matches/get_info/{partners_id}")
    Call<UserDTO> getMatchInfo(@Path("usr_id") String user_id, @Path("partners_id") String partners_id);

    @DELETE("user/{usr_id}/connections/matches/delete/")
    void deleteMatch(@Path("usr_id") String user_id);

    @GET("user/{usr_id}/auth/")
    Call<Boolean> userAuth(@Body AuthDTO authDTO);
}
