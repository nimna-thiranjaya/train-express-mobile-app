package com.example.trainbookingapp.network;

import com.example.trainbookingapp.model.request.AddReservationRequest;
import com.example.trainbookingapp.model.response.GetReservationResponse;
import com.example.trainbookingapp.model.response.StandardResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReservationApiService {
    String prefix = "reservations";

    @POST(prefix + "/moblie-create")
    Call<StandardResponse<GetReservationResponse>> createReservation(@Body AddReservationRequest addReservationRequest);

    @GET(prefix + "/by-nic/{nic}")
    Call<StandardResponse<ArrayList<GetReservationResponse>>> getReservationByNic(@Path("nic") String nic);

    @GET(prefix + "/{id}")
    Call<StandardResponse<GetReservationResponse>> getReservationById(@Path("id") String id);

    @DELETE(prefix + "/{id}")
    Call<StandardResponse> deleteReservationById(@Path("id") String id);
}
