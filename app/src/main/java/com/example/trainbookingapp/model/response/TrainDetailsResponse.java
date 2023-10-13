package com.example.trainbookingapp.model.response;

import com.google.gson.annotations.SerializedName;

public class TrainDetailsResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("trainNumber")
    private String trainNumber;

    @SerializedName("name")
    private String name;

    @SerializedName("firstClassSeatCount")
    private int firstClassSeatCount;

    @SerializedName("firstClassSeatPrice")
    private double firstClassSeatPrice;

    @SerializedName("secondClassSeatCount")
    private int secondClassSeatCount;

    @SerializedName("secondClassSeatPrice")
    private double secondClassSeatPrice;

    @SerializedName("thirdClassSeatCount")
    private int thirdClassSeatCount;

    @SerializedName("thirdClassSeatPrice")
    private double thirdClassSeatPrice;

    @SerializedName("vipClassSeatCount")
    private int vipClassSeatCount;

    @SerializedName("vipClassSeatPrice")
    private double vipClassSeatPrice;

    @SerializedName("otherInfo")
    private String otherInfo;

    @SerializedName("published")
    private boolean published;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    // Add getters and setters as needed

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFirstClassSeatCount() {
        return firstClassSeatCount;
    }

    public void setFirstClassSeatCount(int firstClassSeatCount) {
        this.firstClassSeatCount = firstClassSeatCount;
    }

    public double getFirstClassSeatPrice() {
        return firstClassSeatPrice;
    }

    public void setFirstClassSeatPrice(double firstClassSeatPrice) {
        this.firstClassSeatPrice = firstClassSeatPrice;
    }

    public int getSecondClassSeatCount() {
        return secondClassSeatCount;
    }

    public void setSecondClassSeatCount(int secondClassSeatCount) {
        this.secondClassSeatCount = secondClassSeatCount;
    }

    public TrainDetailsResponse(String id, String trainNumber, String name, int firstClassSeatCount, double firstClassSeatPrice, int secondClassSeatCount, double secondClassSeatPrice, int thirdClassSeatCount, double thirdClassSeatPrice, int vipClassSeatCount, double vipClassSeatPrice, String otherInfo, boolean published, String createdAt, String updatedAt) {
        this.id = id;
        this.trainNumber = trainNumber;
        this.name = name;
        this.firstClassSeatCount = firstClassSeatCount;
        this.firstClassSeatPrice = firstClassSeatPrice;
        this.secondClassSeatCount = secondClassSeatCount;
        this.secondClassSeatPrice = secondClassSeatPrice;
        this.thirdClassSeatCount = thirdClassSeatCount;
        this.thirdClassSeatPrice = thirdClassSeatPrice;
        this.vipClassSeatCount = vipClassSeatCount;
        this.vipClassSeatPrice = vipClassSeatPrice;
        this.otherInfo = otherInfo;
        this.published = published;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "TrainDetailsResponse{" +
                "id='" + id + '\'' +
                ", trainNumber='" + trainNumber + '\'' +
                ", name='" + name + '\'' +
                ", firstClassSeatCount=" + firstClassSeatCount +
                ", firstClassSeatPrice=" + firstClassSeatPrice +
                ", secondClassSeatCount=" + secondClassSeatCount +
                ", secondClassSeatPrice=" + secondClassSeatPrice +
                ", thirdClassSeatCount=" + thirdClassSeatCount +
                ", thirdClassSeatPrice=" + thirdClassSeatPrice +
                ", vipClassSeatCount=" + vipClassSeatCount +
                ", vipClassSeatPrice=" + vipClassSeatPrice +
                ", otherInfo='" + otherInfo + '\'' +
                ", published=" + published +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }

    public double getSecondClassSeatPrice() {
        return secondClassSeatPrice;
    }

    public void setSecondClassSeatPrice(double secondClassSeatPrice) {
        this.secondClassSeatPrice = secondClassSeatPrice;
    }

    public int getThirdClassSeatCount() {
        return thirdClassSeatCount;
    }

    public void setThirdClassSeatCount(int thirdClassSeatCount) {
        this.thirdClassSeatCount = thirdClassSeatCount;
    }

    public double getThirdClassSeatPrice() {
        return thirdClassSeatPrice;
    }

    public void setThirdClassSeatPrice(double thirdClassSeatPrice) {
        this.thirdClassSeatPrice = thirdClassSeatPrice;
    }

    public int getVipClassSeatCount() {
        return vipClassSeatCount;
    }

    public void setVipClassSeatCount(int vipClassSeatCount) {
        this.vipClassSeatCount = vipClassSeatCount;
    }

    public double getVipClassSeatPrice() {
        return vipClassSeatPrice;
    }

    public void setVipClassSeatPrice(double vipClassSeatPrice) {
        this.vipClassSeatPrice = vipClassSeatPrice;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
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
}