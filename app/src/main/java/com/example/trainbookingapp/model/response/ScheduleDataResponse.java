package com.example.trainbookingapp.model.response;

import com.google.gson.annotations.SerializedName;

public class ScheduleDataResponse {
    @SerializedName("schedule")
    private ScheduleResponse scheduleResponse;

    @SerializedName("trainDetails")
    private TrainDetailsResponse trainDetailsResponse;

    public ScheduleResponse getScheduleResponse() {
        return scheduleResponse;
    }

    public void setScheduleResponse(ScheduleResponse scheduleResponse) {
        this.scheduleResponse = scheduleResponse;
    }

    public TrainDetailsResponse getTrainDetailsResponse() {
        return trainDetailsResponse;
    }

    public void setTrainDetailsResponse(TrainDetailsResponse trainDetailsResponse) {
        this.trainDetailsResponse = trainDetailsResponse;
    }

    public ScheduleDataResponse(ScheduleResponse scheduleResponse, TrainDetailsResponse trainDetailsResponse) {
        this.scheduleResponse = scheduleResponse;
        this.trainDetailsResponse = trainDetailsResponse;
    }

    @Override
    public String toString() {
        return "ScheduleDataResponse{" +
                "scheduleResponse=" + scheduleResponse +
                ", trainDetailsResponse=" + trainDetailsResponse +
                '}';
    }
// Add getters and setters as needed
}
