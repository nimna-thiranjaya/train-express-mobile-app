package com.example.trainbookingapp.model.response;

import com.google.gson.annotations.SerializedName;

public class ScheduleResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("trainId")
    private String trainId;

    @SerializedName("departureStation")
    private String departureStation;

    @SerializedName("destinationStation")
    private String destinationStation;

    @SerializedName("departureDate")
    private String departureDate;

    @SerializedName("arrivalDate")
    private String arrivalDate;

    @SerializedName("recurringDays")
    private String recurringDays;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    @Override
    public String toString() {
        return "ScheduleResponse{" +
                "id='" + id + '\'' +
                ", trainId='" + trainId + '\'' +
                ", departureStation='" + departureStation + '\'' +
                ", destinationStation='" + destinationStation + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", recurringDays='" + recurringDays + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }


    public ScheduleResponse(String id, String trainId, String departureStation, String destinationStation, String departureDate, String arrivalDate, String recurringDays, String createdAt, String updatedAt) {
        this.id = id;
        this.trainId = trainId;
        this.departureStation = departureStation;
        this.destinationStation = destinationStation;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.recurringDays = recurringDays;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getRecurringDays() {
        return recurringDays;
    }

    public void setRecurringDays(String recurringDays) {
        this.recurringDays = recurringDays;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
// Add getters and setters as needed

}
