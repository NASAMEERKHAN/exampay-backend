package com.sameer.exampay.entity;

import jakarta.persistence.*;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String receiver;   // CSE101
    private String message;
    private String studentId;
    private String type;       // STUDENT / HOD
    private boolean isRead;
    private String createdAt;
    private boolean isHidden;
    private String semester;


    // GETTERS & SETTERS

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }


    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public boolean isHidden(){return isHidden;}
    public void setHidden(boolean hidden){this.isHidden=hidden;}


    public String getSemester(){return semester;}
    public void setSemester(String semester){ this.semester=semester;}


}