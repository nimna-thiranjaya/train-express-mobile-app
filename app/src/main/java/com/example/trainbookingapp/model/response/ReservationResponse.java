package com.example.trainbookingapp.model.response;

import com.google.gson.annotations.SerializedName;

public class ReservationResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("scheduleId")
    private String scheduleId;

    @SerializedName("nic")
    private String nic;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("nationality")
    private String nationality;

    @SerializedName("departureStation")
    private String departureStation;

    @SerializedName("destinationStation")
    private String destinationStation;

    @SerializedName("seatType")
    private String seatType;

    @SerializedName("seatCount")
    private int seatCount;

    @SerializedName("totalPrice")
    private int totalPrice;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
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

    public ReservationResponse(String id, String scheduleId, String nic, String firstName, String lastName, String email, String mobile, String nationality, String departureStation, String destinationStation, String seatType, int seatCount, int totalPrice, String createdAt, String updatedAt) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.nic = nic;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.nationality = nationality;
        this.departureStation = departureStation;
        this.destinationStation = destinationStation;
        this.seatType = seatType;
        this.seatCount = seatCount;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ReservationResponse{" +
                "id='" + id + '\'' +
                ", scheduleId='" + scheduleId + '\'' +
                ", nic='" + nic + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", nationality='" + nationality + '\'' +
                ", departureStation='" + departureStation + '\'' +
                ", destinationStation='" + destinationStation + '\'' +
                ", seatType='" + seatType + '\'' +
                ", seatCount=" + seatCount +
                ", totalPrice=" + totalPrice +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}