package com.project1.www.UserModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import java.io.Serializable;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_username_email_number", columnList = "username,useremail,number")
})
public class usermodel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Email
    private String useremail;

    @Column(name = "email")
    private String email; // Additional email field

    private Long number;

    @Column(name = "auth_provider")
    private String authProvider = "MANUAL";

    @Column(name = "status")
    private String status = "OFFLINE";

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "customer_id", unique = true)
    private String customerId; // 12-digit string

    @Column(name = "account_non_locked", nullable = false)
    private Boolean accountNonLocked = true; // Default value

    @Column(name = "failed_attempt", nullable = false)
    private Integer failedAttempt = 0; // Default value

    public usermodel() {
    }

    // getters & setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
        this.email = useremail; // Sync both email fields
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Integer getFailedAttempt() {
        return failedAttempt;
    }

    public void setFailedAttempt(Integer failedAttempt) {
        this.failedAttempt = failedAttempt;
    }

}
