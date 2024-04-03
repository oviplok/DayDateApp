package com.example.datedictator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.datedictator.repository.dto.UserDTO;
import com.example.datedictator.retrofit.ApiService;
import com.example.datedictator.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends AndroidViewModel {
    public AuthViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<UserDTO> data;

    public LiveData<UserDTO> getUserById(String userID) {
        if (data == null) {
            data = new MutableLiveData<>();
            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            Call<UserDTO> call = apiService.getUserById(userID);
            call.enqueue(new Callback<UserDTO>() {

                @Override
                public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                    data.setValue(response.body());
                }

                @Override
                public void onFailure(Call<UserDTO> call, Throwable t) {
                    // Handle failure
                }
            });
        }
        return data;
    }
}
