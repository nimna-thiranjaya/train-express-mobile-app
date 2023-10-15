package com.example.trainbookingapp;

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

import com.example.trainbookingapp.db.DatabaseHelper;
import com.example.trainbookingapp.model.response.ErrorResponse;
import com.example.trainbookingapp.model.response.GetReservationResponse;
import com.example.trainbookingapp.model.response.StandardResponse;
import com.example.trainbookingapp.network.ReservationApiService;
import com.example.trainbookingapp.network.RetrofitClient;
import com.example.trainbookingapp.network.ScheduleApiService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    private TicketAdapter ticketAdapter;

    private DatabaseHelper databaseHelper;

    private EditText search_text;

    private List<TicketDomain> ticketDomains;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);

        recyclerView = view.findViewById(R.id.viewTicketView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        search_text = view.findViewById(R.id.search_text);
        ticketAdapter = new TicketAdapter(new ArrayList<>());
        recyclerView.setAdapter(ticketAdapter);

        fetchDataFromApi();

        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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
        ReservationApiService reservationApiService = RetrofitClient.getRetrofitInstance().create(ReservationApiService.class);

        databaseHelper = new DatabaseHelper(getContext());
        String nic = databaseHelper.getAllTravelerData();

        Call<StandardResponse<ArrayList<GetReservationResponse>>> call = reservationApiService.getReservationByNic(nic);

        call.enqueue(new Callback<StandardResponse<ArrayList<GetReservationResponse>>>() {
            @Override
            public void onResponse(Call<StandardResponse<ArrayList<GetReservationResponse>>> call, Response<StandardResponse<ArrayList<GetReservationResponse>>> response) {
                if(response.isSuccessful()){
                    StandardResponse<ArrayList<GetReservationResponse>> standardResponse = response.body();

                    ArrayList<GetReservationResponse> getReservationResponses = standardResponse.getData();

                    ticketDomains = convertToTicketDomain(getReservationResponses);

                    adapter = new TicketAdapter(ticketDomains);
                    recyclerView.setAdapter(adapter);

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
            public void onFailure(Call<StandardResponse<ArrayList<GetReservationResponse>>> call, Throwable t) {
                Log.d("TrainFragment", "onFailure: " + t.getLocalizedMessage());
            }
        });

    }

    private List<TicketDomain>  convertToTicketDomain (ArrayList<GetReservationResponse> getReservationResponse) {
        List<TicketDomain> result = new ArrayList<>();

        for(GetReservationResponse getReservationResponses : getReservationResponse){
            String depTime = getReservationResponses.getScheduleWithTrainDetailsResponse().getScheduleResponse().getDepartureDate().split("T")[1];
            TicketDomain ticketDomain = new TicketDomain(
                    getReservationResponses.getReservationResponse().getDepartureStation(),
                    getReservationResponses.getReservationResponse().getDestinationStation(),
                    String.valueOf(getReservationResponses.getReservationResponse().getSeatCount()),
                    getReservationResponses.getScheduleWithTrainDetailsResponse().getScheduleResponse().getDepartureDate().split("T")[0],
                    getReservationResponses.getReservationResponse().getSeatType(),
                    depTime.split("Z")[0],
                    getReservationResponses.getUserResponse().getNic()
            );
            result.add(ticketDomain);
        }
        return result;
    }

    private void filterData(String query) {
        List<TicketDomain> filteredList = new ArrayList<>();

        for (TicketDomain item : ticketDomains) {
            if (item.getDesStation().toLowerCase().contains(query.toLowerCase()) || item.getDepDate().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }

        // Update the RecyclerView with the filtered data
        adapter = new TicketAdapter(filteredList);
        recyclerView.setAdapter(adapter);
    }
}
