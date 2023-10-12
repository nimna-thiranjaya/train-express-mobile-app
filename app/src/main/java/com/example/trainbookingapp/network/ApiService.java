package com.example.trainbookingapp.network;

import com.example.trainbookingapp.model.LoginRequest;
import com.example.trainbookingapp.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    public String prefix = "Traveler";

    @POST(prefix + "/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
