package com.example.trainbookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.trainbookingapp.Auth.SignInActivity;

public class BookReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_reservation);

        Button EditbookBtn = findViewById(R.id.editBtn);
        Button CancelBookBtn = findViewById(R.id.rescancelbtn);

        EditbookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditReservationActivity.class);
                startActivity(i);
            }


        });

        CancelBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HomeFragment.class);
                startActivity(i);
            }


        });
    }
}