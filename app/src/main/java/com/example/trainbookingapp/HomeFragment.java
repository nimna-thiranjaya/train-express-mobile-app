package com.example.trainbookingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainbookingapp.db.DatabaseHelper;
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

public class HomeFragment extends Fragment {
    private DatabaseHelper databaseHelper;

    private TextView userName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        databaseHelper = new DatabaseHelper(getContext());
        String id = databaseHelper.getAllTravelerData();
        userName = view.findViewById(R.id.userName);

        fetchTravelerData();

        return view;
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void fetchTravelerData() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        databaseHelper = new DatabaseHelper(getContext());
        String id = databaseHelper.getAllTravelerData();

        Call<StandardResponse<UserResponse>> call = apiService.getTravelerById(id);

        call.enqueue(new Callback<StandardResponse<UserResponse>>() {
            @Override
            public void onResponse(Call<StandardResponse<UserResponse>> call, Response<StandardResponse<UserResponse>> response) {
                if (response.isSuccessful()) {
                    StandardResponse<UserResponse> loginResponse = response.body();
                    if(loginResponse.getData() != null){
                        UserResponse userResponse = loginResponse.getData();
                        userName.setText(userResponse.getFirstName() + " " + userResponse.getLastName());
                    }else{
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