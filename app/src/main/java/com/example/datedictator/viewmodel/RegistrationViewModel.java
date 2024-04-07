package com.example.datedictator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.datedictator.repository.dto.AuthDTO;
import com.example.datedictator.repository.dto.UserDTO;
import com.example.datedictator.retrofit.RetrofitClient;
import com.example.datedictator.retrofit.UserApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationViewModel  extends AndroidViewModel{
    private MutableLiveData<AuthDTO> authData;

    private MutableLiveData<UserDTO> data;
    private UserApiService userApiService;
    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        userApiService = RetrofitClient.getClient().create(UserApiService.class);
    }

    public void userRegistration(UserDTO userDTO){
        userApiService.addNewUser(userDTO);
    }

    private boolean result;

    public boolean checkUser(AuthDTO authDTO) {
            authData = new MutableLiveData<>();
            UserApiService userApiService = RetrofitClient.getClient().create(UserApiService.class);
//            Call<UserDTO> call = userApiService.getUserById(userID);
            Call<String> call = userApiService.userAuth(authDTO);

        call.enqueue(new Callback<String>() {


                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    result = response.body() != null;
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

        return result;
    }

    //Check
}
