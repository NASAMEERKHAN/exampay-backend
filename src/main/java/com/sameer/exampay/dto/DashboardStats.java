package com.sameer.exampay.dto;

public class DashboardStats {

    private long totalStudents;
    private long paidStudents;
    private long defaulters;
    private long totalExams;
    private long totalHallTickets;

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getPaidStudents() {
        return paidStudents;
    }

    public void setPaidStudents(long paidStudents) {
        this.paidStudents = paidStudents;
    }

    public long getDefaulters() {
        return defaulters;
    }

    public void setDefaulters(long defaulters) {
        this.defaulters = defaulters;
    }

    public long getTotalExams() {
        return totalExams;
    }

    public void setTotalExams(long totalExams) {
        this.totalExams = totalExams;
    }

    public long getTotalHallTickets() {
        return totalHallTickets;
    }

    public void setTotalHallTickets(long totalHallTickets) {
        this.totalHallTickets = totalHallTickets;
    }
}