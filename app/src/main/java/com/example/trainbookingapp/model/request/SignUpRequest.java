package com.example.trainbookingapp.model.request;

import com.google.gson.annotations.SerializedName;

public class SignUpRequest {

        @SerializedName("nic")
        private String nic;

        @SerializedName("firstName")
        private String firstName;

        @SerializedName("lastName")
        private String lastName;

        @SerializedName("imageUrl")
        private String imageUrl;

        @SerializedName("passwordHash")
        private String passwordHash;

        @SerializedName("email")
        private String email;

        @SerializedName("mobile")
        private String mobile;

        @SerializedName("nationality")
        private String nationality;

        @SerializedName("status")
        private boolean status;

        @SerializedName("role")
        private String role;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public SignUpRequest(String nic, String firstName, String lastName, String imageUrl, String passwordHash, String email, String mobile, String nationality, boolean status, String role) {
        this.nic = nic;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
        this.passwordHash = passwordHash;
        this.email = email;
        this.mobile = mobile;
        this.nationality = nationality;
        this.status = status;
        this.role = role;
    }

    @Override
    public String toString() {
        return "SignRequest{" +
                "nic='" + nic + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", nationality='" + nationality + '\'' +
                ", status=" + status +
                ", role='" + role + '\'' +
                '}';
    }
}
