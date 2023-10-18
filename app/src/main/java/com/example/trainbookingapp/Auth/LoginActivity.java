package com.example.trainbookingapp.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trainbookingapp.MainActivity;
import com.example.trainbookingapp.R;
import com.example.trainbookingapp.db.DatabaseHelper;
import com.example.trainbookingapp.db.TravelerDAO;
import com.example.trainbookingapp.model.request.LoginRequest;
import com.example.trainbookingapp.model.request.SignUpRequest;
import com.example.trainbookingapp.model.response.ErrorResponse;
import com.example.trainbookingapp.model.response.LoginResponse;
import com.example.trainbookingapp.model.response.SignUpResponse;
import com.example.trainbookingapp.model.response.StandardResponse;
import com.example.trainbookingapp.model.response.UserResponse;
import com.example.trainbookingapp.network.ApiService;
import com.example.trainbookingapp.network.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextText8, editTextPassword;
    private Button loginbtn;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button Registerbtn = findViewById(R.id.Registerbtn);
        loginbtn = findViewById(R.id.loginbtn);
        editTextText8 = findViewById(R.id.editTextText8);
        editTextPassword = findViewById(R.id.editTextPassword);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        String id = databaseHelper.getAllTravelerData();

        if(id != null){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }
        });

        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(i);
            }


        });

    }

    private void showToast(String message) {
        Context context = getApplicationContext();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

    private void setData() {
        String email = editTextText8.getText().toString();
        String password = editTextPassword.getText().toString();
        Boolean check = false;

        //send alert if email or password is empty
        if (email.isEmpty()) {
            editTextText8.setError("Email is required");
            editTextText8.requestFocus();
            check = true;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            check = true;
        }

        if (check) {
            return;
        } else {
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            final LoginRequest loginRequest = new LoginRequest(email, password);

            Call<StandardResponse<LoginResponse>> call = apiService.login(loginRequest);
            databaseHelper = new DatabaseHelper(LoginActivity.this);
            call.enqueue(new Callback<StandardResponse<LoginResponse>>() {
                @Override
                public void onResponse(Call<StandardResponse<LoginResponse>> call, Response<StandardResponse<LoginResponse>> response) {
                    if (response.isSuccessful()) {
                        StandardResponse<LoginResponse> loginResponse = response.body();
                        if (loginResponse.getData() != null) {
                            databaseHelper.saveTravelerData(
                                    loginResponse.getData().getTraveler().getNic()
                            );

                            Log.d("LoginActivity", "Login Response: " + databaseHelper.getAllTravelerData().toString());
                            showToast("Login Successful!");
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        } else {
                            showToast("Email or Password Invalid!");
                        }
                    } else {
                        try {
                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);

                            String errorMessage = errorResponse.getMessage();
                            Log.d("LoginActivity", "Error Message: " + errorMessage);
                            showToast(errorMessage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<StandardResponse<LoginResponse>> call, Throwable t) {
                    Log.d("LoginActivity", "Error: " + t.getMessage());

                }
            });

        }
    }

}