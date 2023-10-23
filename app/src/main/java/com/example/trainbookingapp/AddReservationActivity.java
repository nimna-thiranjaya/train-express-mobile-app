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

import com.example.trainbookingapp.Auth.SignInActivity;
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

public class AddReservationActivity extends AppCompatActivity {
    EditText traveller_name, email_edit, number_edit, SeatCountfill;
    Spinner spinner1;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);
        Button addreservationBtn = findViewById(R.id.nextstepbtn);
        Intent i = getIntent();
        String scheduleId = i.getStringExtra("scheduleId");
        databaseHelper = new DatabaseHelper(getApplicationContext());
        String nic = databaseHelper.getAllTravelerData();

        Log.d("AddReservationActivity", "onCreate: " + scheduleId);

        traveller_name = findViewById(R.id.traveller_name);
        email_edit = findViewById(R.id.email_edit);
        number_edit = findViewById(R.id.number_edit);
        SeatCountfill = findViewById(R.id.SeatCountfill);
        spinner1 = findViewById(R.id.spinner1);

        fetchTravelerData();


        Spinner seatCategorySpinner = findViewById(R.id.spinner1);


        addreservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String travellerName = traveller_name.getText().toString();
                String email = email_edit.getText().toString();
                String mobile = number_edit.getText().toString();
                String seatCount = SeatCountfill.getText().toString();
                String seatCategory = seatCategorySpinner.getSelectedItem().toString();

                Boolean isValid = true;

                if (travellerName.isEmpty()) {
                    traveller_name.setError("Traveller name is required");
                    isValid = false;
                }

                if (email.isEmpty()) {
                    email_edit.setError("Email is required");
                    isValid = false;
                }

                if (!email.isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    email_edit.setError("Invalid email address");
                    isValid = false;
                }

                if (mobile.isEmpty()) {
                    number_edit.setError("Mobile is required");
                    isValid = false;
                }

                if (!mobile.isEmpty() && mobile.length() != 10) {
                    number_edit.setError("Mobile number should be 10 digits");
                    isValid = false;
                }

                if (seatCount.isEmpty()) {
                    SeatCountfill.setError("Seat count is required");
                    isValid = false;
                }

                if (!seatCount.isEmpty() && Integer.parseInt(seatCount) <= 0) {
                    SeatCountfill.setError("Seat count cannot be more than 0");
                    isValid = false;
                }

                if(seatCategory.equals("Select Seat Type")) {
                    ((android.widget.TextView)seatCategorySpinner.getSelectedView()).setError("Select Seat Type");
                    spinner1.requestFocus();
                    isValid = false;
                }

                if (!isValid) {
                    return;
                } else {
                    ReservationApiService reservationApiService = RetrofitClient.getRetrofitInstance().create(ReservationApiService.class);
                    AddReservationRequest addReservationRequest = new AddReservationRequest(scheduleId, nic, seatCategory, Integer.parseInt(seatCount));

                    Call<StandardResponse<GetReservationResponse>> call = reservationApiService.createReservation(addReservationRequest);

                    call.enqueue(new Callback<StandardResponse<GetReservationResponse>>() {
                        @Override
                        public void onResponse(Call<StandardResponse<GetReservationResponse>> call, Response<StandardResponse<GetReservationResponse>> response) {
                            if (response.isSuccessful()) {
                                StandardResponse<GetReservationResponse> standardResponse = response.body();

                                String reservationId = standardResponse.getData().getReservationResponse().getId();

                                Intent i = new Intent(getApplicationContext(), BookReservationActivity.class);
                                i.putExtra("reservationId", reservationId);
                                startActivity(i);

                                Log.d("AddReservationActivity", "onResponse: " + standardResponse.getData().toString());
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
                            Log.d("AddReservationActivity", "onFailure: " + t.getLocalizedMessage());
                        }
                    });
                }
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.seat_categories, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        seatCategorySpinner.setAdapter(adapter);

        // Set "3A" as the default value
        int defaultPosition = adapter.getPosition("Select Seat Type");
        seatCategorySpinner.setSelection(defaultPosition);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void fetchTravelerData() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        String id = databaseHelper.getAllTravelerData();

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
}
