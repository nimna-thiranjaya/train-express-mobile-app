package com.example.trainbookingapp.model.request;

import com.google.gson.annotations.SerializedName;

public class UserStatusUpdateReuest {
    @SerializedName("status")
    private Boolean status;

    public UserStatusUpdateReuest(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserStatusUpdateReuest{" +
                "status='" + status + '\'' +
                '}';
    }
}
