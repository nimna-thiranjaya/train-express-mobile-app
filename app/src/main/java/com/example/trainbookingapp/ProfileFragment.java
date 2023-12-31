package com.example.trainbookingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainbookingapp.Auth.LoginActivity;
import com.example.trainbookingapp.db.DatabaseHelper;
import com.example.trainbookingapp.model.request.UserStatusUpdateReuest;
import com.example.trainbookingapp.model.response.ErrorResponse;
import com.example.trainbookingapp.model.response.LoginResponse;
import com.example.trainbookingapp.model.response.StandardResponse;
import com.example.trainbookingapp.model.response.UserResponse;
import com.example.trainbookingapp.network.ApiService;
import com.example.trainbookingapp.network.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView mobileTextView;
    private TextView nicTextView;
    private TextView roleTextView;

    private DatabaseHelper databaseHelper;

    private Button deActiveBtn,editButton,LogoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nameTextView = view.findViewById(R.id.nameTextView);
        emailTextView = view.findViewById(R.id.email);
        firstNameTextView = view.findViewById(R.id.firstNameTextView);  // Check the IDs, update if needed
        lastNameTextView = view.findViewById(R.id.lastNameTextView);   // Check the IDs, update if needed
        mobileTextView = view.findViewById(R.id.mobileTextView);
        nicTextView = view.findViewById(R.id.nicTextView);
        roleTextView = view.findViewById(R.id.roleTextView);
        editButton = view.findViewById(R.id.editButton);
        deActiveBtn = view.findViewById(R.id.deActiveBtn);
        LogoutBtn = view.findViewById(R.id.LogoutBtn);

        fetchTravelerData();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogout();
            }
        });

        deActiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deActiveUserAccount();
            }
        });
        return view;
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
                    if (loginResponse.getData() != null) {
                        UserResponse userResponse = loginResponse.getData();
                        nameTextView.setText(userResponse.getFirstName() + " " + userResponse.getLastName());
                        emailTextView.setText(userResponse.getEmail());
                        firstNameTextView.setText(userResponse.getFirstName());
                        lastNameTextView.setText(userResponse.getLastName());
                        mobileTextView.setText(userResponse.getMobile());
                        nicTextView.setText(userResponse.getNic());
                        roleTextView.setText(userResponse.getNationality());
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


    public void deActiveUserAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to deactivate your account?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                String userId = databaseHelper.getAllTravelerData();
                ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
                UserStatusUpdateReuest userStatusUpdateReuest = new UserStatusUpdateReuest(false);
                Call<StandardResponse> call = apiService.updateTravelerStatus(userId, userStatusUpdateReuest);

                call.enqueue(new Callback<StandardResponse>() {
                    @Override
                    public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                        if(response.isSuccessful()){
                            StandardResponse body = response.body();
                            showToast(body.getMessage());
                            databaseHelper.deleteAllTravelers();
                            Intent intent = new Intent(getContext(), LoginActivity.class);
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
                        Log.d("ProfileFragment", "onFailure: " + t.getMessage());
                    }
                });

                dialog.dismiss();
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

    public void onLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to logout?");

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                databaseHelper.deleteAllTravelers();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                dialog.dismiss();
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

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void refreshProfile() {
        fetchTravelerData();
    }
}
