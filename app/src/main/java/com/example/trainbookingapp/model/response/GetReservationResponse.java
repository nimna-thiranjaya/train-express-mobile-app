package com.example.trainbookingapp.model.response;

import com.google.gson.annotations.SerializedName;

public class GetReservationResponse {
    @SerializedName("reservation")
    ReservationResponse reservationResponse;

    @SerializedName("traveler")
    UserResponse userResponse;

    @SerializedName("scheduleWithTrainDetails")
    ScheduleWithTrainDetailsResponse scheduleWithTrainDetailsResponse;

    public ReservationResponse getReservationResponse() {
        return reservationResponse;
    }

    public void setReservationResponse(ReservationResponse reservationResponse) {
        this.reservationResponse = reservationResponse;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }

    public ScheduleWithTrainDetailsResponse getScheduleWithTrainDetailsResponse() {
        return scheduleWithTrainDetailsResponse;
    }

    public void setScheduleWithTrainDetailsResponse(ScheduleWithTrainDetailsResponse scheduleWithTrainDetailsResponse) {
        this.scheduleWithTrainDetailsResponse = scheduleWithTrainDetailsResponse;
    }

    public GetReservationResponse(ReservationResponse reservationResponse, UserResponse userResponse, ScheduleWithTrainDetailsResponse scheduleWithTrainDetailsResponse) {
        this.reservationResponse = reservationResponse;
        this.userResponse = userResponse;
        this.scheduleWithTrainDetailsResponse = scheduleWithTrainDetailsResponse;
    }

    @Override
    public String toString() {
        return "GetReservationResponse{" +
                "reservationResponse=" + reservationResponse +
                ", userResponse=" + userResponse +
                ", scheduleWithTrainDetailsResponse=" + scheduleWithTrainDetailsResponse +
                '}';
    }
}
