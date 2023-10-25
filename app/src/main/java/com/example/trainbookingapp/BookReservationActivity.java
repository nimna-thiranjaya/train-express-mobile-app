package com.example.trainbookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainbookingapp.model.response.ErrorResponse;
import com.example.trainbookingapp.model.response.GetReservationResponse;
import com.example.trainbookingapp.model.response.StandardResponse;
import com.example.trainbookingapp.network.ReservationApiService;
import com.example.trainbookingapp.network.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookReservationActivity extends AppCompatActivity {

    TextView train_no,departure_station,destination_station,seat_count,seat_category,total_price,discount;

    Button BookNowBtn,rescancelbtn,editBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_reservation);

        train_no = findViewById(R.id.train_no);
        departure_station = findViewById(R.id.departure_station);
        destination_station = findViewById(R.id.destination_station);
        seat_count = findViewById(R.id.seat_count);
        seat_category = findViewById(R.id.seat_category);
        total_price = findViewById(R.id.total_price);
        discount = findViewById(R.id.discount);
        BookNowBtn = findViewById(R.id.BookNowBtn);
        rescancelbtn = findViewById(R.id.rescancelbtn);
        editBtn = findViewById(R.id.editBtn);

        Intent i = getIntent();
        String reservationId = i.getStringExtra("reservationId");
        getReservationDetais(reservationId);

        BookNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Reservation Booked Successfully");
                Intent intent = new Intent(BookReservationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookReservationActivity.this, EditReservationActivity.class);
                intent.putExtra("reservationId", reservationId);
                startActivity(intent);
            }
        });


        rescancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to cancel this Reservation?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cancelReservation(reservationId);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                // Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    private void getReservationDetais(String reservationId) {
       String resId = reservationId;

        ReservationApiService reservationApiService = RetrofitClient.getRetrofitInstance().create(ReservationApiService.class);

        Call<StandardResponse<GetReservationResponse>> call = reservationApiService.getReservationById(resId);

        call.enqueue(new Callback<StandardResponse<GetReservationResponse>>() {
            @Override
            public void onResponse(Call<StandardResponse<GetReservationResponse>> call, Response<StandardResponse<GetReservationResponse>> response) {
                if(response.isSuccessful()) {
                    Log.d("BookReservationActivity", "onResponse: " + response.body().getData().toString());
                    GetReservationResponse reservationResponse = response.body().getData();

                    train_no.setText(reservationResponse.getScheduleWithTrainDetailsResponse().getTrainDetailsResponse().getTrainNumber());
                    departure_station.setText(reservationResponse.getReservationResponse().getDepartureStation());
                    destination_station.setText(reservationResponse.getReservationResponse().getDestinationStation());
                    seat_category.setText(reservationResponse.getReservationResponse().getSeatType());

                    Integer sheetCount = reservationResponse.getReservationResponse().getSeatCount();

                    if(sheetCount > 10) {
                        seat_count.setText(sheetCount.toString());
                        discount.setText("5%");

                        Double discountPrice = reservationResponse.getReservationResponse().getTotalPrice() * 0.05;
                        Double totalPrice = reservationResponse.getReservationResponse().getTotalPrice() - discountPrice;
                        total_price.setText("LKR " + totalPrice.toString());
                    }else{
                        seat_count.setText(sheetCount.toString());
                        discount.setText("0%");

                        total_price.setText("LKR " + reservationResponse.getReservationResponse().getTotalPrice());
                    }

                }else{
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
            public void onFailure(Call<StandardResponse<GetReservationResponse>> call, Throwable t) {
                Log.d("BookReservationActivity", "onFailure: " + t.getLocalizedMessage());
            }


        });
    }
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void cancelReservation(String reservationId) {
        ReservationApiService reservationApiService = RetrofitClient.getRetrofitInstance().create(ReservationApiService.class);


        Call<StandardResponse> call = reservationApiService.deleteReservationById(reservationId);


            call.enqueue(new Callback<StandardResponse>() {
                @Override
                public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                    if(response.isSuccessful()){
                        Log.d("BookReservationActivity", "onResponse: " + response.body().getMessage());
                        showToast(response.body().getMessage());
                        Intent intent = new Intent(BookReservationActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else{
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
                public void onFailure(Call<StandardResponse> call, Throwable t) {
                    Log.d("BookReservationActivity", "onFailure: " + t.getLocalizedMessage());
                }
            });
    }
}