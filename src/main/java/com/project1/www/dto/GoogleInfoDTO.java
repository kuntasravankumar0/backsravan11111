package com.project1.www.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class GoogleInfoDTO {

    @NotBlank(message = "Google ID is required")
    private String googleId;

    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    private Boolean emailVerified;

    private String picture;

    private String locale;

    private String authProvider;

    // Default constructor
    public GoogleInfoDTO() {}

    // Constructor with parameters
    public GoogleInfoDTO(String googleId, String name, String email, Boolean emailVerified, 
                        String picture, String locale, String authProvider) {
        this.googleId = googleId;
        this.name = name;
        this.email = email;
        this.emailVerified = emailVerified;
        this.picture = picture;
        this.locale = locale;
        this.authProvider = authProvider;
    }

    // Getters and Setters
    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(String authProvider) {
        this.authProvider = authProvider;
    }

    @Override
    public String toString() {
        return "GoogleInfoDTO{" +
                "googleId='" + googleId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", emailVerified=" + emailVerified +
                ", authProvider='" + authProvider + '\'' +
                '}';
    }
}