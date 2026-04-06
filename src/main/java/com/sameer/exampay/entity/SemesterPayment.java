package com.sameer.exampay.entity;

import jakarta.persistence.*;

@Entity
@Table(name="semester_payment")

public class SemesterPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hallTicketNumber;

    private String semester;

    private Double amount;

    private String status;

    // getters and setters

    public Long getId() {
        return id;
    }

    public String getHallTicketNumber() {
        return hallTicketNumber;
    }

    public void setHallTicketNumber(String hallTicketNumber) {
        this.hallTicketNumber = hallTicketNumber;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}