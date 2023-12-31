package com.example.trainbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.trainbookingapp.databinding.ActivityMainBinding;

public class Viewholder2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewholder2);

        // Find the clickable area (ConstraintLayout) by its ID
        View clickableArea = findViewById(R.id.clickticket);

        // Set OnClickListener to the clickable area
        clickableArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the Reservation_details activity when the clickable area is clicked
                Intent intent = new Intent(Viewholder2.this, Reservation_details.class);
                startActivity(intent);
            }
        });
    }
}
