package com.sameer.exampay.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String semester;
    private double amount;
    private String status;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // getters & setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
}