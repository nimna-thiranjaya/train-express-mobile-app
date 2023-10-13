package com.example.trainbookingapp.network;

import com.example.trainbookingapp.model.response.ScheduleDataResponse;
import com.example.trainbookingapp.model.response.ScheduleResponse;
import com.example.trainbookingapp.model.response.StandardResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ScheduleApiService {
    String prefix = "Schedule";

    @GET(prefix + "")
    Call<StandardResponse<ArrayList<ScheduleDataResponse>>> getAllSchedules();

    @GET(prefix + "/{id}")
    Call<StandardResponse<ScheduleDataResponse>> getScheduleById(@Path("id") String id);
}
