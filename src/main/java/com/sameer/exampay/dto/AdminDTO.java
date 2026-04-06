package com.sameer.exampay.dto;

import java.util.List;

public class AdminDTO {

    private String studentId;
    private String department;
    private String status;
    private List<SemesterDTO> semesters;

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<SemesterDTO> getSemesters() { return semesters; }
    public void setSemesters(List<SemesterDTO> semesters) { this.semesters = semesters; }
}