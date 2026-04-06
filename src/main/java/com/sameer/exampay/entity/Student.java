package com.sameer.exampay.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(name = "hall_ticket_number", unique = true)
    private String hallTicketNumber;

    private String dept;

    private String type;

    private String password;

    private String branch;

    private int year;

    private int semester;

    private String approvalStatus;

    private boolean paid;

    private String admissionType;

    @JsonManagedReference
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Semester> semesters;

    // getters & setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }



    public String getHallTicketNumber() { return hallTicketNumber; }
    public void setHallTicketNumber(String hallTicketNumber) { this.hallTicketNumber = hallTicketNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public String getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }

    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }

    public String getAdmissionType() { return admissionType; }
    public void setAdmissionType(String admissionType) { this.admissionType = admissionType; }

    public List<Semester> getSemesters() { return semesters; }
    public void setSemesters(List<Semester> semesters) { this.semesters = semesters; }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }


}