package com.example.trainbookingapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.trainbookingapp.model.response.LoginResponse;

public class TravelerDAO {
    private DatabaseHelper databaseHelper;

    public TravelerDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void insertTraveler(LoginResponse.Traveler traveler) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nic", traveler.getNic());
        values.put("first_name", traveler.getFirstName());
        values.put("last_name", traveler.getLastName());
        values.put("email", traveler.getEmail());
        values.put("role", traveler.getRole());

        db.insert("traveler", null, values);
        db.close();
    }
}