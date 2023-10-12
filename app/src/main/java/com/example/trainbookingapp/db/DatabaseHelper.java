package com.example.trainbookingapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.trainbookingapp.model.LoginResponse;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "train__db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TRAVELER = "traveler";
    private static final String COLUMN_NIC = "nic";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_ROLE = "role";

    private static final String CREATE_TABLE_TRAVELER =
            "CREATE TABLE " + TABLE_TRAVELER + "("
                    + "id" + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_FIRST_NAME + " TEXT"
                    + COLUMN_LAST_NAME + "TEXT"
                    + COLUMN_EMAIL + "TEXT"
                    + COLUMN_ROLE + "TEXT"
                    + COLUMN_NIC + "TEXT"
                    + ")";
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

}
