package com.example.datedictator.viewmodel.settings;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.datedictator.repository.dto.UserDTO;
import com.example.datedictator.repository.model.Card;
import com.example.datedictator.retrofit.RetrofitClient;
import com.example.datedictator.retrofit.UserApiService;

import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsViewModel extends AndroidViewModel {

    private MutableLiveData<Map> data;
    private UserApiService userApiService;
    public SettingsViewModel(@NonNull Application application) {
        super(application);
        userApiService = RetrofitClient.getClient().create(UserApiService.class);
    }

    Map<String,String> userInfo;
    public LiveData<Map> getUserInfo(String userId){

        Call<UserDTO> call = userApiService.getUserById(userId);

        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if(response.body()!=null){
                    if(response.body().getId()!=null){
                        userInfo.put("userId",response.body().getId());
                    }
                    if(response.body().getName()!=null){
                        userInfo.put("name",response.body().getName());
                    }
                    if(response.body().getPhone()!=null){
                        userInfo.put("phone",response.body().getPhone());
                    }
                    if(response.body().getProfileImageUrl()!=null){
                        userInfo.put("profileImageUrl",
                                response.body().getProfileImageUrl());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.e("SettingsViewModel:", "getUserInfo: " + t);
            }
        });

        return data;
    }

    private UserDTO newData;
    public void UpdateUserInfo(Map userInfo){
        Call<UserDTO> call = userApiService.getUserById(userInfo.get("userId").toString());

        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if(response.body()!=null){
                   newData = response.body();
                }

            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.e("SettingsViewModel:", "getUserInfo: " + t);
            }
        });

        newData.setName(userInfo.get("name").toString());
        newData.setPhone(userInfo.get("phone").toString());
        Objects.requireNonNull(userInfo.get("profileImageUrl")).toString();
        newData.setProfileImageUrl(Objects.requireNonNull(
                userInfo.get("profileImageUrl")).toString());

        userApiService.updateUserProfile(userInfo.get("userId").toString(),newData);
    }
}
