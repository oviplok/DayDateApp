package com.example.datedictator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.datedictator.repository.dto.UserDTO;
import com.example.datedictator.retrofit.RetrofitClient;
import com.example.datedictator.retrofit.UserApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRegistrationViewModel extends AndroidViewModel {
    public LoginRegistrationViewModel(@NonNull Application application) {
        super(application);
    }

    private boolean result;
    public boolean checkUserExist(String userId){
        UserApiService userApiService = RetrofitClient.getClient().create(UserApiService.class);
        Call<UserDTO> call = userApiService.getUserById(userId);
        call.enqueue(new Callback<UserDTO>() {

            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if(response.body()!=null){
                    result = true;
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                // Handle failure
            }
        });
        return result;
    }
}
