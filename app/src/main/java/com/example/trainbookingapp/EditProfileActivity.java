package com.example.trainbookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trainbookingapp.db.DatabaseHelper;
import com.example.trainbookingapp.model.request.UserEditRequest;
import com.example.trainbookingapp.model.response.ErrorResponse;
import com.example.trainbookingapp.model.response.StandardResponse;
import com.example.trainbookingapp.model.response.UserResponse;
import com.example.trainbookingapp.network.ApiService;
import com.example.trainbookingapp.network.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    private EditText firstName, lastName, email, phoneNumber, nic, nationality;
    private DatabaseHelper databaseHelper;

    private Button updateBtn;

    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);
        nic = findViewById(R.id.nic);
        nationality = findViewById(R.id.nationality);
        updateBtn = findViewById(R.id.updateBtn);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        id = databaseHelper.getAllTravelerData();

        fetchTravelerData();

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });


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

                        firstName.setText(userResponse.getFirstName());
                        lastName.setText(userResponse.getLastName());
                        email.setText(userResponse.getEmail());
                        email.setEnabled(false);
                        phoneNumber.setText(userResponse.getMobile());
                        nic.setText(userResponse.getNic());
                        nic.setEnabled(false);
                        nationality.setText(userResponse.getNationality());
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

    private void updateUserProfile() {
        String FirstName = firstName.getText().toString();
        String LastName = lastName.getText().toString();
        String Email = email.getText().toString();
        String PhoneNumber = phoneNumber.getText().toString();
        String Nic = nic.getText().toString();
        String Nationality = nationality.getText().toString();

        Boolean check = false;

        if (FirstName.isEmpty()) {
            firstName.setError("First name is required");
            firstName.requestFocus();
            check = true;
        }

        if (LastName.isEmpty()) {
            lastName.setError("Last name is required");
            lastName.requestFocus();
            check = true;
        }

        if (Email.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            check = true;
        }

        if (PhoneNumber.isEmpty()) {
            phoneNumber.setError("Phone number is required");
            phoneNumber.requestFocus();
            check = true;
        }

        if (PhoneNumber.length() != 10) {
            phoneNumber.setError("Phone number should be 10 digits");
            phoneNumber.requestFocus();
            check = true;
        }


        if (Nic.isEmpty()) {
            nic.setError("NIC is required");
            nic.requestFocus();
            check = true;
        }

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        UserEditRequest userEditRequest = new UserEditRequest(Nic, FirstName, LastName, "", Email, PhoneNumber, Nationality);

        Log.d("ProfileFragment", "updateUserProfile: " + userEditRequest.toString());
        Call<StandardResponse> call = apiService.updateTraveler(id, userEditRequest);

        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if(response.isSuccessful()){
                    Log.d("ProfileFragment", "onResponse: " + response.body().getMessage());
                    showToast(response.body().getMessage());
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

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
                Log.d("ProfileFragment", "onFailure: " + t.getLocalizedMessage());
            }
        });

    }
}