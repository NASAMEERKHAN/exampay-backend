package com.sameer.exampay.entity;

import jakarta.persistence.*;

@Entity
public class Hod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Column(name = "department")   
    private String dept;

    
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDept() {
        return dept;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
