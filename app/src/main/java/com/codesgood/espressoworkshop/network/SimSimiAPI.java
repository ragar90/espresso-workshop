package com.codesgood.espressoworkshop.network;

import com.codesgood.espressoworkshop.network.models.Answer;
import com.codesgood.espressoworkshop.network.models.PersonalityAnswer;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface SimSimiAPI {

    @GET("/request.p?key=556aca5c-1e0b-4f7b-b7f9-d5d06e815d4f")
    void getAnswer(@Query("lc") String language, @Query("text") String message, Callback<Answer> callback);

    @GET("/api/chat/?apiKey=CH7oztvUnrQOSxlW&chatBotID=23958")
    void getAnswerPersonality(@Query("externalID") String id, @Query("message") String message, Callback<PersonalityAnswer> callback);
}
