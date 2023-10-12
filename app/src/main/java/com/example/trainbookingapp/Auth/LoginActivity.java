package com.example.trainbookingapp.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainbookingapp.MainActivity;
import com.example.trainbookingapp.R;
import com.example.trainbookingapp.db.DatabaseHelper;
import com.example.trainbookingapp.db.TravelerDAO;
import com.example.trainbookingapp.model.LoginRequest;
import com.example.trainbookingapp.model.LoginResponse;
import com.example.trainbookingapp.network.ApiService;
import com.example.trainbookingapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextText8, editTextPassword;
    private Button loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button Registerbtn = findViewById(R.id.Registerbtn);
        loginbtn = findViewById(R.id.loginbtn);
        editTextText8 = findViewById(R.id.editTextText8);
        editTextPassword = findViewById(R.id.editTextPassword);

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

//        loginbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(i);
//            }
//
//
//        });
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
            final TravelerDAO travelerDAO = new TravelerDAO(getApplicationContext());
            Call<LoginResponse> call = apiService.login(loginRequest);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        showToast("Login Successful!");
                        Log.d("LoginActivity", "Responses: " + loginResponse.getTraveler().getFirstName());
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    } else {
                        showToast("Login Failed!");
                        Log.d("LoginActivity", "Error: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("LoginActivity", "Error: " + t.getMessage());

                }
            });

        }
    }

}