package com.sameer.exampay.dto;

public class SemesterDTO {

    private String semester;
    private double amount;
    private String status;

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}