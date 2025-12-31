package com.project1.www.UserModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "google_info", indexes = {
        @Index(name = "idx_google_id", columnList = "googleId"),
        @Index(name = "idx_email", columnList = "email")
})
public class GoogleInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "google_id", unique = true, nullable = false)
    private String googleId;

    @Column(name = "name")
    private String name;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified = false;

    @Column(name = "picture", length = 500)
    private String picture;

    @Column(name = "locale")
    private String locale;

    @Column(name = "auth_provider", nullable = false)
    private String authProvider = "GOOGLE";

    @Column(name = "status")
    private String status = "OFFLINE";

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Default constructor
    public GoogleInfo() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.authProvider = "GOOGLE"; // Set default value
    }

    // Constructor with parameters
    public GoogleInfo(String googleId, String name, String email, Boolean emailVerified,
            String picture, String locale, String authProvider) {
        this();
        this.googleId = googleId;
        this.name = name;
        this.email = email;
        this.emailVerified = emailVerified;
        this.picture = picture;
        this.locale = locale;
        this.authProvider = authProvider;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "GoogleInfo{" +
                "id=" + id +
                ", googleId='" + googleId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", emailVerified=" + emailVerified +
                ", authProvider='" + authProvider + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}