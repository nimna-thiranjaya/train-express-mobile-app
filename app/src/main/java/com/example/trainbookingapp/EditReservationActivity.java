package com.example.trainbookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.trainbookingapp.db.DatabaseHelper;
import com.example.trainbookingapp.model.request.AddReservationRequest;
import com.example.trainbookingapp.model.response.ErrorResponse;
import com.example.trainbookingapp.model.response.GetReservationResponse;
import com.example.trainbookingapp.model.response.StandardResponse;
import com.example.trainbookingapp.model.response.UserResponse;
import com.example.trainbookingapp.network.ApiService;
import com.example.trainbookingapp.network.ReservationApiService;
import com.example.trainbookingapp.network.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditReservationActivity extends AppCompatActivity {
    EditText traveller_name, email_edit, number_edit, SeatCountfill;
    DatabaseHelper databaseHelper;

    Spinner spinner1;

    String reservationId;

    private String seatCategory;

    String sheduleId;

    Button nextstepbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reservation);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        String id = databaseHelper.getAllTravelerData();

        traveller_name = findViewById(R.id.traveller_name);
        email_edit = findViewById(R.id.email_edit);
        number_edit = findViewById(R.id.number_edit);
        SeatCountfill = findViewById(R.id.SeatCountfill);
        spinner1 = findViewById(R.id.spinner1);
        nextstepbtn = findViewById(R.id.nextstepbtn);
        Intent i = getIntent();
        reservationId = i.getStringExtra("reservationId");

        fetchTravelerData(id);
        getReservationDetais(reservationId);

        nextstepbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upadteReservation(reservationId, id, sheduleId);
            }
        });

    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void fetchTravelerData(String id) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<StandardResponse<UserResponse>> call = apiService.getTravelerById(id);

        call.enqueue(new Callback<StandardResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<StandardResponse<UserResponse>> call, Response<StandardResponse<UserResponse>> response) {
                if (response.isSuccessful()) {
                    StandardResponse<UserResponse> loginResponse = response.body();
                    if (loginResponse.getData() != null) {
                        UserResponse userResponse = loginResponse.getData();
                        traveller_name.setText(userResponse.getFirstName() + " " + userResponse.getLastName());
                        email_edit.setText(userResponse.getEmail());
                        number_edit.setText(userResponse.getMobile());
                        // success
                        Log.d("ProfileFragment", "onResponse: " + loginResponse.getData().toString());
                    } else {
                        showToast("No data found");
                    }
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
            public void onFailure(Call<StandardResponse<UserResponse>> call, Throwable t) {
                Log.d("ProfileFragment", "Error: " + t.getMessage());
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
                if (response.isSuccessful()) {
//                    Log.d("BookReservationActivity", "onResponse: " + response.body().getData().toString());
                    GetReservationResponse reservationResponse = response.body().getData();
                    int seatCount = reservationResponse.getReservationResponse().getSeatCount();
                    String seatCountString = String.valueOf(seatCount);
                    seatCategory = reservationResponse.getReservationResponse().getSeatType().toString();
                    sheduleId = reservationResponse.getScheduleWithTrainDetailsResponse().getScheduleResponse().getId().toString();
                    Log.d("BookReservationActivity", "onResponse: " + seatCategory);

                    Spinner seatCategorySpinner = findViewById(R.id.spinner1);

                    // Create an ArrayAdapter using the string array and a default spinner layout
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(EditReservationActivity.this,
                            R.array.seat_categories, android.R.layout.simple_spinner_item);

                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Apply the adapter to the spinner
                    seatCategorySpinner.setAdapter(adapter);

                    int defaultPosition = adapter.getPosition(seatCategory);
                    seatCategorySpinner.setSelection(defaultPosition);
                    SeatCountfill.setText(seatCountString);

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
            public void onFailure(Call<StandardResponse<GetReservationResponse>> call, Throwable t) {
                Log.d("BookReservationActivity", "onFailure: " + t.getLocalizedMessage());
            }


        });
    }

    private void upadteReservation(String reservationId, String id, String sheduleId) {
        String seatCategory = spinner1.getSelectedItem().toString();
        String seatCount = SeatCountfill.getText().toString();
        String email = email_edit.getText().toString();
        String mobile = number_edit.getText().toString();
        String name = traveller_name.getText().toString();


        Boolean isValidate = false;

        if (seatCount.isEmpty()) {
            SeatCountfill.setError("Seat Count is required");
            SeatCountfill.requestFocus();
            isValidate = true;
        }

        if (email.isEmpty()) {
            email_edit.setError("Email is required");
            email_edit.requestFocus();
            isValidate = true;
        }

        //email validation
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_edit.setError("Enter a valid email");
            email_edit.requestFocus();
            isValidate = true;
        }

        if (mobile.isEmpty()) {
            number_edit.setError("Mobile is required");
            number_edit.requestFocus();
            isValidate = true;
        }

        if (name.isEmpty()) {
            traveller_name.setError("Name is required");
            traveller_name.requestFocus();
            isValidate = true;
        }

        if (isValidate) {
            return;
        } else {
            ReservationApiService reservationApiService = RetrofitClient.getRetrofitInstance().create(ReservationApiService.class);

            AddReservationRequest reqUpdateReq = new AddReservationRequest(sheduleId, id, seatCategory,Integer.parseInt(seatCount));

            Log.d("EditReservationActivity", "upadteReservation: " + reqUpdateReq.toString());

            Call<StandardResponse> call = reservationApiService.updateReservationById(reservationId, reqUpdateReq);

            call.enqueue(new Callback<StandardResponse>() {
                @Override
                public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                    if(response.isSuccessful()){
                        showToast(response.body().getMessage());
                        Intent intent = new Intent(getApplicationContext(), BookReservationActivity.class);
                        intent.putExtra("reservationId", reservationId);
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
}