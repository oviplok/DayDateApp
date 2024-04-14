package com.example.datedictator.viewmodel.registration;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.datedictator.repository.dto.AuthDTO;
import com.example.datedictator.repository.dto.UserDTO;
import com.example.datedictator.retrofit.UserApiService;
import com.example.datedictator.retrofit.RetrofitClient;
import com.example.datedictator.utils.enums.HTTPResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends AndroidViewModel {

    private UserApiService userApiService;
    public AuthViewModel(@NonNull Application application) {
        super(application);
        userApiService = RetrofitClient.getClient().create(UserApiService.class);
    }
    HTTPResponse httpResponse;

    private MutableLiveData<UserDTO> userData;
    private MutableLiveData<AuthDTO> authData;

    public LiveData<UserDTO> getUserById(String userID) {
        if (userData == null) {
            userData = new MutableLiveData<>();
//            UserApiService
            Call<UserDTO> call = userApiService.getUserById(userID);
            call.enqueue(new Callback<UserDTO>() {

                @Override
                public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                    userData.setValue(response.body());
//                    return false;
                }

                @Override
                public void onFailure(Call<UserDTO> call, Throwable t) {
                    // Handle failure
                }
            });
        }
        return userData;
    }

    private String result;
    public String getUserId(AuthDTO authDTO) {

//            UserApiService userApiService = RetrofitClient.getClient().create(UserApiService.class);
//            Call<UserDTO> call = userApiService.getUserById(userID);
            Call<String> call = userApiService.userAuth(authDTO);
            call.enqueue(new Callback<String>() {


                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.body()!=null){
                        result = response.body();
                    }
                    else{
                        result = "NO_RESULT";
                    }
//                    return false;
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("AuthViewModel:", "getUserId: " + t);
                }
            });
        return result;
    }


}
