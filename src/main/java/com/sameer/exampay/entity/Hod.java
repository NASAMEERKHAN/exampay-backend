package com.sameer.exampay.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Hod {

    @Id
    private String username;

    private String password;
    private String department;

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}