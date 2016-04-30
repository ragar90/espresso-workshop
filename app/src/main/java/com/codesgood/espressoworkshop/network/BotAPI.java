package com.codesgood.espressoworkshop.network;

import com.codesgood.espressoworkshop.network.models.Answer;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface BotAPI {

    @GET("/api/chat/?apiKey=CH7oztvUnrQOSxlW&chatBotID=23958")
    void getAnswerPersonality(@Query("externalID") String id, @Query("message") String message, Callback<Answer> callback);
}
