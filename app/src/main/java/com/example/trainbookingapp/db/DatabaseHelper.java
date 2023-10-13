package com.example.trainbookingapp.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.trainbookingapp.model.response.SignUpResponse;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "train__db";
    private static final int DATABASE_VERSION = 1;

    private static final String ID_COL = "id";
    private static final String TABLE_TRAVELER = "traveler";
    private static final String COLUMN_NIC = "nic";
    private static final String CREATE_TABLE_TRAVELER =
            "CREATE TABLE " + TABLE_TRAVELER + " ("
                    + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NIC + " TEXT NOT NULL)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TRAVELER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, and create a fresh table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAVELER);
        onCreate(db);
    }

    // Method to save traveler data to SQLite
    public void saveTravelerData(String nic) {
        deleteAllTravelers();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NIC, nic);

        db.insert(TABLE_TRAVELER, null, values);
        db.close();
    }

    public void deleteAllTravelers(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_TRAVELER);
        db.close();
    }

    public String getAllTravelerData() {
        List<String> travelerList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] columns = {COLUMN_NIC};

        Cursor cursor = db.query(
                TABLE_TRAVELER,  // Table name
                columns,         // Columns to retrieve
                null,            // Selection (WHERE clause), null for all rows
                null,            // Selection arguments
                null,            // Group by
                null,            // Having
                null             // Order by
        );

        // Iterate through the cursor and add data to the list
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nic = cursor.getString(cursor.getColumnIndex(COLUMN_NIC));
                travelerList.add(nic);
            } while (cursor.moveToNext());

            // Close the cursor
            cursor.close();
        }

        // Close the database connection
        db.close();

        return travelerList.get(0);
    }

}
