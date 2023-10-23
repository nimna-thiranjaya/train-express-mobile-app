package com.example.trainbookingapp.network;

import com.example.trainbookingapp.model.request.LoginRequest;
import com.example.trainbookingapp.model.request.SignUpRequest;
import com.example.trainbookingapp.model.request.UserEditRequest;
import com.example.trainbookingapp.model.request.UserStatusUpdateReuest;
import com.example.trainbookingapp.model.response.LoginResponse;
import com.example.trainbookingapp.model.response.SignUpResponse;
import com.example.trainbookingapp.model.response.StandardResponse;
import com.example.trainbookingapp.model.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    public String prefix = "Traveler";

    @POST(prefix + "/login")
    Call<StandardResponse<LoginResponse>> login(@Body LoginRequest loginRequest);

    @POST(prefix + "")
    Call<StandardResponse<SignUpResponse>> signUp(@Body SignUpRequest signUpRequest);

    @GET(prefix + "/{id}")
    Call<StandardResponse<UserResponse>> getTravelerById(@Path("id") String id);

    @PUT(prefix + "/{nic}")
    Call<StandardResponse> updateTraveler(@Path("nic") String id, @Body UserEditRequest userEditRequest);

    @PUT(prefix + "/{nic}/status")
    Call<StandardResponse> updateTravelerStatus(@Path("nic") String nic, @Body UserStatusUpdateReuest userStatusUpdateReuest);
}
