package com.example.trainbookingapp.model.request;

import com.google.gson.annotations.SerializedName;

public class AddReservationRequest {
    @SerializedName("scheduleId")
    private String scheduleId;

    @SerializedName("nic")
    private String nic;

    @SerializedName("seatType")
    private String seatType;

    @SerializedName("seatCount")
    private int seatCount;

    public AddReservationRequest(String scheduleId, String nic, String seatType, int seatCount) {
        this.scheduleId = scheduleId;
        this.nic = nic;
        this.seatType = seatType;
        this.seatCount = seatCount;
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

    public String getSeatType() {
        return seatType;
    }

    @Override
    public String toString() {
        return "AddReservationRequest{" +
                "scheduleId='" + scheduleId + '\'' +
                ", nic='" + nic + '\'' +
                ", seatType='" + seatType + '\'' +
                ", seatCount=" + seatCount +
                '}';
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
}