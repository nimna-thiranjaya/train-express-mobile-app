package com.example.trainbookingapp.model.response;

import com.google.gson.annotations.SerializedName;

public class ScheduleWithTrainDetailsResponse {
    @SerializedName("schedule")
    private ScheduleResponse scheduleResponse;

    @SerializedName("trainDetails")
    private TrainDetailsResponse trainDetailsResponse;

    public ScheduleResponse getScheduleResponse() {
        return scheduleResponse;
    }

    @Override
    public String toString() {
        return "ScheduleWithTrainDetailsResponse{" +
                "scheduleResponse=" + scheduleResponse +
                ", trainDetailsResponse=" + trainDetailsResponse +
                '}';
    }

    public ScheduleWithTrainDetailsResponse(ScheduleResponse scheduleResponse, TrainDetailsResponse trainDetailsResponse) {
        this.scheduleResponse = scheduleResponse;
        this.trainDetailsResponse = trainDetailsResponse;
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
}
