package com.example.trainbookingapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trainbookingapp.model.response.ErrorResponse;
import com.example.trainbookingapp.model.response.ScheduleDataResponse;
import com.example.trainbookingapp.model.response.ScheduleResponse;
import com.example.trainbookingapp.model.response.StandardResponse;
import com.example.trainbookingapp.network.RetrofitClient;
import com.example.trainbookingapp.network.ScheduleApiService;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private MostViewAdapter mostViewAdapter;
    private EditText search_text;
   private List<MostViewedDomain> mostViewedDomains;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_train, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.viewMostView);
        search_text = (EditText) view.findViewById(R.id.search_text);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        mostViewAdapter = new MostViewAdapter(new ArrayList<>());
        recyclerView.setAdapter(mostViewAdapter);

        fetchDataFromApi();

        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used in this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter the data based on the search query
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    private void fetchDataFromApi() {
        ScheduleApiService scheduleApiService = RetrofitClient.getRetrofitInstance().create(ScheduleApiService.class);

        Call<StandardResponse<ArrayList<ScheduleDataResponse>>> call = scheduleApiService.getAllSchedules();

        call.enqueue(new Callback<StandardResponse<ArrayList<ScheduleDataResponse>>>() {
            @Override
            public void onResponse(Call<StandardResponse<ArrayList<ScheduleDataResponse>>> call, Response<StandardResponse<ArrayList<ScheduleDataResponse>>> response) {
                if (response.isSuccessful()) {
                    StandardResponse<ArrayList<ScheduleDataResponse>> standardResponse = response.body();

                   ArrayList<ScheduleDataResponse> scheduleDataResponses = standardResponse.getData();

                    mostViewedDomains = convertToMostViewedDomains(scheduleDataResponses);

                    adapter = new MostViewAdapter(mostViewedDomains);
                    recyclerView.setAdapter(adapter);

                   Log.d("TrainFragment", "onResponse: " + scheduleDataResponses);

                }else {
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
            public void onFailure(Call<StandardResponse<ArrayList<ScheduleDataResponse>>> call, Throwable t) {
                Log.d("TrainFragment", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private List<MostViewedDomain> convertToMostViewedDomains(ArrayList<ScheduleDataResponse> trainDataList) {
        List<MostViewedDomain> result = new ArrayList<>();

        for(ScheduleDataResponse trainData : trainDataList){
            Log.d("TrainFragment", "convertToMostViewedDomains: " + trainData.toString());

            MostViewedDomain mostViewedDomain = new MostViewedDomain(
                    trainData.getScheduleResponse().getDepartureStation() + "-" + trainData.getScheduleResponse().getDestinationStation(),
                    "Arrival At " + trainData.getScheduleResponse().getArrivalDate().split("T")[0] + ", Departure At " + trainData.getScheduleResponse().getDepartureDate().split("T")[0],
                    "",
                    trainData.getScheduleResponse().getId()
            );

            result.add(mostViewedDomain);
        }

        return result;
    }

    private void filterData(String query) {
        List<MostViewedDomain> filteredList = new ArrayList<>();

        for (MostViewedDomain item : mostViewedDomains) {
            if (item.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }

        // Update the RecyclerView with the filtered data
        adapter = new MostViewAdapter(filteredList);
        recyclerView.setAdapter(adapter);
    }
}
