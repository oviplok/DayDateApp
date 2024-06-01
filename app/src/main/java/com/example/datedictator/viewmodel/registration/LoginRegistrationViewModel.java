package com.example.datedictator.viewmodel.registration;

import android.app.Application;
import android.util.Log;

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

    private UserApiService userApiService;
    public LoginRegistrationViewModel(@NonNull Application application) {
        super(application);
        userApiService = RetrofitClient.getClient().create(UserApiService.class);
    }

    private boolean result;
    public boolean checkUserExist(String userId){

        Call<UserDTO> call = userApiService.getUserById(userId);
        call.enqueue(new Callback<UserDTO>() {

            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if(response.body()!=null){
                    result = true;
                }
//                return false;
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                // Handle failure
                Log.e("LoginRegistrationViewModel:", "checkUserExist: " + t);
            }
        });
        return result;
    }
}
