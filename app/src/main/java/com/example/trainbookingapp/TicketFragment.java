package com.example.trainbookingapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class TicketFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);

        recyclerView = view.findViewById(R.id.viewTicketView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<TicketDomain> ticketviewed = new ArrayList<>();
        ticketviewed.add(new TicketDomain("Pettah", "Matara", "5", "2023 oct 23","Second Class","2.00 am","200003800410"));
        ticketviewed.add(new TicketDomain("Pettah", "Matara", "5", "2023 oct 23","Second Class","2.00 am","200003800410"));
        ticketviewed.add(new TicketDomain("Pettah", "Matara", "5", "2023 oct 23","Second Class","2.00 am","200003800410"));

        // Create an instance of TicketAdapter and pass the ArrayList to its constructor
        adapter = new TicketAdapter(ticketviewed);

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);

        return view;
    }
}
