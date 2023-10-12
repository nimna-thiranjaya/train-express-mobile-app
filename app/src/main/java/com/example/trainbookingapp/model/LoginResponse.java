package com.example.trainbookingapp.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("traveler")
    private Traveler traveler;

    public Traveler getTraveler() {
        return traveler;
    }

    public static class Traveler {

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

        public void setNic(String nic) {
            this.nic = nic;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public Traveler(String nic, String firstName, String lastName, String imageUrl, String passwordHash, String email, String mobile, String nationality, boolean status, String role, String createdAt, String updatedAt) {
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
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        @Override
        public String toString() {
            return "Traveler{" +
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
                    ", createdAt='" + createdAt + '\'' +
                    ", updatedAt='" + updatedAt + '\'' +
                    '}';
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public void setPasswordHash(String passwordHash) {
            this.passwordHash = passwordHash;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        @SerializedName("role")
        private String role;

        @SerializedName("createdAt")
        private String createdAt;

        @SerializedName("updatedAt")
        private String updatedAt;

        // Add getters for each field

        public String getNic() {
            return nic;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getPasswordHash() {
            return passwordHash;
        }

        public String getEmail() {
            return email;
        }

        public String getMobile() {
            return mobile;
        }

        public String getNationality() {
            return nationality;
        }

        public boolean isStatus() {
            return status;
        }

        public String getRole() {
            return role;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }
}
