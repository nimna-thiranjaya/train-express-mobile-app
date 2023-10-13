package com.example.trainbookingapp;

public class TicketDomain {

    private String DepStation;
    private String DesStation;
    private String Seats;
    private String DepDate;
    private String UClass;
    private String DepTime;
    private String UserNic;


    public TicketDomain(String depStation, String desStation, String seats, String depDate, String UClass, String depTime, String userNic) {
        this.DepStation = depStation;
        this.DesStation = desStation;
        this.Seats = seats;
        this.DepDate = depDate;
        this.UClass = UClass;
        this.DepTime = depTime;
        this.UserNic = userNic;
    }


    public String getDepStation() {
        return DepStation;
    }

    public void setDepStation(String depStation) {
        DepStation = depStation;
    }

    public String getDesStation() {
        return DesStation;
    }

    public void setDesStation(String desStation) {
        DesStation = desStation;
    }

    public String getSeats() {
        return Seats;
    }

    public void setSeats(String seats) {
        Seats = seats;
    }

    public String getDepDate() {
        return DepDate;
    }

    public void setDepDate(String depDate) {
        DepDate = depDate;
    }

    public String getUClass() {
        return UClass;
    }

    public void setUClass(String UClass) {
        this.UClass = UClass;
    }

    public String getDepTime() {
        return DepTime;
    }

    public void setDepTime(String depTime) {
        DepTime = depTime;
    }

    public String getUserNic() {
        return UserNic;
    }

    public void setUserNic(String userNic) {
        UserNic = userNic;
    }
}
