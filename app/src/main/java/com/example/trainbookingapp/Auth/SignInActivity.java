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
import com.example.trainbookingapp.model.request.SignUpRequest;
import com.example.trainbookingapp.model.response.ErrorResponse;
import com.example.trainbookingapp.model.response.LoginResponse;
import com.example.trainbookingapp.model.response.SignUpResponse;
import com.example.trainbookingapp.model.response.StandardResponse;
import com.example.trainbookingapp.network.ApiService;
import com.example.trainbookingapp.network.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;

public class SignInActivity extends AppCompatActivity {

    private EditText first_name, last_name, email, phone_number, nic, password, confirm_password;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        phone_number = findViewById(R.id.phone_number);
        nic = findViewById(R.id.nic);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        signUpButton = findViewById(R.id.signUpButton);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
            }
        });
    }

    private void showToast(String message) {
        Context context = getApplicationContext();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

    private void setData() {
        String firstName = first_name.getText().toString();
        String lastName = last_name.getText().toString();
        String email = this.email.getText().toString();
        String mobile = phone_number.getText().toString();
        String nic = this.nic.getText().toString();
        String password = this.password.getText().toString();
        String confirmPassword = confirm_password.getText().toString();

        Log.d("TAG", "setData: " + firstName + " " + lastName + " " + email + " " + mobile + " " + nic + " " + password + " " + confirmPassword);
        Boolean check = false;

        if (firstName.isEmpty()) {
            first_name.setError("Fist name is required");
            first_name.requestFocus();
            check = true;
        }

        if (lastName.isEmpty()) {
            last_name.setError("Last name is required");
            last_name.requestFocus();
            check = true;
        }

        if (email.isEmpty()) {
            this.email.setError("Email is required");
            this.email.requestFocus();
            check = true;
        }

        if (mobile.isEmpty()) {
            phone_number.setError("Mobile number is required");
            phone_number.requestFocus();
            check = true;
        }

        if (nic.isEmpty()) {
            this.nic.setError("NIC is required");
            this.nic.requestFocus();
            check = true;
        }

        if (password.isEmpty()) {
            this.password.setError("Password is required");
            this.password.requestFocus();
            check = true;
        }

        if (confirmPassword.isEmpty()) {
            confirm_password.setError("Confirm password is required");
            confirm_password.requestFocus();
            check = true;
        }

        if (!password.equals(confirmPassword)) {
            confirm_password.setError("Password and confirm password should be same");
            confirm_password.requestFocus();
            check = true;
        }

        if (check) {
            Log.d("SignUp", "setData: " + firstName + " " + lastName + " " + email + " " + mobile + " " + nic + " " + password + " " + confirmPassword);
            return;
        } else {
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            SignUpRequest signUpRequest = new SignUpRequest(nic, firstName, lastName, "", password, email, mobile, "Sri Lankan", true, "User");

            Call<StandardResponse<SignUpResponse>> call = apiService.signUp(signUpRequest);

            call.enqueue(new retrofit2.Callback<StandardResponse<SignUpResponse>>() {
                @Override
                public void onResponse(Call<StandardResponse<SignUpResponse>> call, retrofit2.Response<StandardResponse<SignUpResponse>> response) {
                    if (response.isSuccessful()) {
                        StandardResponse<SignUpResponse> loginResponse = response.body();
                        if(loginResponse.getData() != null){
                            showToast("User create Successful!");
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                        }else{
                            showToast("Something went wrong");
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
                public void onFailure(Call<StandardResponse<SignUpResponse>> call, Throwable t) {
                    Log.d("LoginActivity", "Error: " + t.getMessage());
                }
            });


        }

    }

}