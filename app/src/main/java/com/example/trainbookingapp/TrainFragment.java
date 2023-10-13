package com.example.trainbookingapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_train, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.viewMostView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

//        ArrayList<MostViewedDomain> mostviewed = new ArrayList<>();
//        mostviewed.add(new MostViewedDomain("hi hutto", "Bruges is one of Europe's best preserved cities", "train_5"));
//        mostviewed.add(new MostViewedDomain("the island Luke Skywalker called home", "Explore Skellig, Ireland's mysterious island outpost", "train_2"));
//        mostviewed.add(new MostViewedDomain("Covid-19 in the Airport", "Traveling this summer? What to know before going to the airport", "train_1"));
//        mostviewed.add(new MostViewedDomain("Browsing Bruges in Belgium", "Bruges is one of Europe's best-preserved cities", "train_3"));
//        mostviewed.add(new MostViewedDomain("Browsing Bruges in Belgium", "Bruges is one of Europe's best preserved cities", "train_5"));
//
//        adapter = new MostViewAdapter(mostviewed);
//        recyclerView.setAdapter(adapter);

        mostViewAdapter = new MostViewAdapter(new ArrayList<>());
        recyclerView.setAdapter(mostViewAdapter);

        fetchDataFromApi();

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

                    List<MostViewedDomain> mostViewedDomains = convertToMostViewedDomains(scheduleDataResponses);

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
}
