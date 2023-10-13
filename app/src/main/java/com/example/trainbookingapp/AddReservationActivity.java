package com.example.trainbookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.trainbookingapp.Auth.SignInActivity;

public class AddReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);
        Button addreservationBtn = findViewById(R.id.nextstepbtn);

        Spinner seatCategorySpinner = findViewById(R.id.spinner1);


        addreservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BookReservationActivity.class);
                startActivity(i);
            }


        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.seat_categories, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        seatCategorySpinner.setAdapter(adapter);

        // Set "3A" as the default value
        int defaultPosition = adapter.getPosition("3A");
        seatCategorySpinner.setSelection(defaultPosition);
    }
}
