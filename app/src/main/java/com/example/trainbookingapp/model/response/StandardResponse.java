package com.example.trainbookingapp.model.response;

import com.google.gson.annotations.SerializedName;

public class StandardResponse<T> {
    @SerializedName("isSuccessful")
    private boolean isSuccessful;

    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private T data;

    // Add constructors, getters, and setters as needed

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StandardResponse{" +
                "isSuccessful=" + isSuccessful +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public StandardResponse(boolean isSuccessful, int statusCode, String message, T data) {
        this.isSuccessful = isSuccessful;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}