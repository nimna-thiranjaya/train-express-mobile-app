package com.example.trainbookingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TrainFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_train, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.viewMostView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<MostViewedDomain> mostviewed = new ArrayList<>();
        mostviewed.add(new MostViewedDomain("Browsing Bruges in Belgium", "Bruges is one of Europe's best preserved cities", "train_1"));
        mostviewed.add(new MostViewedDomain("the island Luke Skywalker called home", "Explore Skellig, Ireland's mysterious island outpost", "train_2"));
        mostviewed.add(new MostViewedDomain("Covid-19 in the Airport", "Traveling this summer? What to know before going to the airport", "train_3"));
        mostviewed.add(new MostViewedDomain("Browsing Bruges in Belgium", "Bruges is one of Europe's best-preserved cities", "train_2"));
        mostviewed.add(new MostViewedDomain("Browsing Bruges in Belgium", "Bruges is one of Europe's best preserved cities", "train_5"));

        adapter = new MostViewAdapter(mostviewed);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
