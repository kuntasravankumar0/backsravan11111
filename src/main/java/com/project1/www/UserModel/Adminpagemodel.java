package com.project1.www.UserModel;

import jakarta.persistence.*;

@Entity
@Table(name = "Admintable")
public class Adminpagemodel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adminname;
    private String email;
    private String password;
    @Column(name = "customer_id", unique = true)
    private String customerId;



    private String status = "PENDING";  // Default status

    // -------------------------
    // GETTERS & SETTERS
    // -------------------------

    public String getCustomerId() {
    return customerId;
}

public void setCustomerId(String customerId) {
    this.customerId = customerId;
}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
