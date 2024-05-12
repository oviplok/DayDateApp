package com.example.datedictator.retrofit;

import com.example.datedictator.repository.dto.ChatDTO;
import com.example.datedictator.repository.dto.MessageDTO;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatApiService {

    @POST("/messages/{chat_id}")
    void sendMessage(@Path("chat_id") String id, @Body MessageDTO message);

    @GET("/messages/{chat_id}")
    List<MessageDTO> getMessages(@Path("chat_id") String id, @Body MessageDTO message);

    @DELETE("/chat/{chat_id}")
    void deleteChat(@Path("chat_id") String id);

    @GET("/chat/{usr_id}")
    List<ChatDTO> findUserChats(@Path("usr_id") String id);

    @GET("/chat/find/{user_id}/{partner_id}")
    ChatDTO findChatByUsers(@Path("user_id") String userId, @Path("partner_id") String partnerId);
}
