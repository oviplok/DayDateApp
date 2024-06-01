package com.example.datedictator.viewmodel;

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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {
    private UserApiService userApiService;

    private MutableLiveData<List<Card>> partnersList;
    Boolean result;
    List<UserDTO> partners;

    public MainViewModel(@NonNull Application application) {
        super(application);
        userApiService = RetrofitClient.getClient().create(UserApiService.class);
    }

    public String checkUserSex(String userID){
       String gender = userApiService.getUserSex(userID).toString();
       String userPrefer = null;

        Log.i("MainViewModel:", "UserSex: " + gender);
        switch (gender) {
            case "Male":
               userPrefer = "Female";
                Log.i("MainViewModel:", "userPrefer: "+userPrefer);
                break;
            case "Female":
                userPrefer = "Male";
                Log.i("MainViewModel:", "userPrefer: "+userPrefer);
                break;
        }

        return userPrefer;
    }

    public LiveData<List<Card>> getPartnersList(String userID,String userPrefer){
        Log.i("MainViewModel:", "getPartners");
        Card userCard;

        List<Card> cardList = new ArrayList<>();
        Call<List<UserDTO>> call = userApiService.getPartners(userID,userPrefer);

        call.enqueue(new Callback<List<UserDTO>>() {
            @Override
            public void onResponse(Call<List<UserDTO>> call, Response<List<UserDTO>> response) {
                    partners = response.body();
            }

            @Override
            public void onFailure(Call<List<UserDTO>> call, Throwable t) {
                // Handle API call failure
                Log.e("MainViewModel:", "getPartners: " + t);
            }
        });

        for (UserDTO partner : partners) {
            Card card = new Card();
            card.setDataFromDTO(partner);
            cardList.add(card);
        }

        partnersList.setValue(cardList);
        return partnersList;
    }

    public void onLeft(String userID, String partnerID){
        userApiService.onLeft(userID,partnerID);

    }

    public Boolean onRight(String userID, String partnerID){
        userApiService.onRight(userID,partnerID);
        return isMatch(userID,partnerID);
    }

    private Boolean isMatch(String userID, String partnerID){
        Call<Boolean> call = userApiService.isMatch(userID, partnerID);

        // Execute the API call asynchronously
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                   result = true;
                } else {
                    result = true;
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                // Handle API call failure
                result=false;
            }
        });
        return result;
    }
}
