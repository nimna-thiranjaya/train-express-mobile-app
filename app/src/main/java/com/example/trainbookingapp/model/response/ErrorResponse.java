package com.example.trainbookingapp.model.response;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
    @SerializedName("isSuccessful")
    private boolean isSuccessful;

    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Object data;  // Use Object if data can be of any type, or replace it with the actual type

    // Add constructors, getters, and setters as needed

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}