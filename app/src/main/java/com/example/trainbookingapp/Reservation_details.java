package com.example.trainbookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainbookingapp.AddReservationActivity;
import com.example.trainbookingapp.Auth.SignInActivity;
import com.example.trainbookingapp.model.response.ErrorResponse;
import com.example.trainbookingapp.model.response.ScheduleDataResponse;
import com.example.trainbookingapp.model.response.ScheduleResponse;
import com.example.trainbookingapp.model.response.StandardResponse;
import com.example.trainbookingapp.network.RetrofitClient;
import com.example.trainbookingapp.network.ScheduleApiService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;

public class Reservation_details extends AppCompatActivity {
    private TextView Scheduler, DepartureR, DestinationR, RunsR, seat, amount, seat2, amount2, seat3, amount3, seat4, amount4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_details);
        // Retrieve data from the Intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String subtitle = intent.getStringExtra("subtitle");
        String id = intent.getStringExtra("id");

        Scheduler = findViewById(R.id.Scheduler);
        DepartureR = findViewById(R.id.DepartureR);
        DestinationR = findViewById(R.id.DestinationR);
        RunsR = findViewById(R.id.RunsR);
        seat = findViewById(R.id.seat);
        amount = findViewById(R.id.amount);
        seat2 = findViewById(R.id.seat2);
        amount2 = findViewById(R.id.amount2);
        seat3 = findViewById(R.id.seat3);
        amount3 = findViewById(R.id.amount3);
        seat4 = findViewById(R.id.seat4);
        amount4 = findViewById(R.id.amount4);

        Log.d("Reservation_details", "onCreate: " + title + " " + subtitle + " " + id);

        Button addRes = findViewById(R.id.ReservationBtn);

        getReservationDetails(id);

        addRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddReservationActivity.class);
                i.putExtra("scheduleId", id);
                startActivity(i);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    private void getReservationDetails(String id) {
        ScheduleApiService apiService = RetrofitClient.getRetrofitInstance().create(ScheduleApiService.class);
        Call<StandardResponse<ScheduleDataResponse>> call = apiService.getScheduleById(id);
        call.enqueue(new retrofit2.Callback<StandardResponse<ScheduleDataResponse>>() {
            @Override
            public void onResponse(Call<StandardResponse<ScheduleDataResponse>> call, retrofit2.Response<StandardResponse<ScheduleDataResponse>> response) {
                if (response.isSuccessful()) {
                    StandardResponse<ScheduleDataResponse> body = response.body();
                    ScheduleDataResponse data = body.getData();

                    Scheduler.setText(data.getTrainDetailsResponse().getTrainNumber());
                    DepartureR.setText(data.getScheduleResponse().getDepartureStation());
                    DestinationR.setText(data.getScheduleResponse().getDestinationStation());
                    RunsR.setText(data.getScheduleResponse().getRecurringDays());
                    seat.setText("A-"+ data.getTrainDetailsResponse().getThirdClassSeatCount());
                    amount.setText("LKR "+ data.getTrainDetailsResponse().getThirdClassSeatPrice());
                    seat2.setText("A-"+ data.getTrainDetailsResponse().getSecondClassSeatCount());
                    amount2.setText("LKR "+ data.getTrainDetailsResponse().getSecondClassSeatPrice());
                    seat3.setText("A-"+ data.getTrainDetailsResponse().getFirstClassSeatCount());
                    amount3.setText("LKR "+ data.getTrainDetailsResponse().getFirstClassSeatPrice());
                    seat4.setText("A-"+ data.getTrainDetailsResponse().getVipClassSeatCount());
                    amount4.setText("LKR "+ data.getTrainDetailsResponse().getVipClassSeatPrice());

                } else {
                    try {
                        Gson gson = new Gson();
                        ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);

                        String errorMessage = errorResponse.getMessage();
                        showToast(errorMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<StandardResponse<ScheduleDataResponse>> call, Throwable t) {
                Log.d("Reservation_details", "onFailure: " + t.getLocalizedMessage());
            }
        });


    }
}