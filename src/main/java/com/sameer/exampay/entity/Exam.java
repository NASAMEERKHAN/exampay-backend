package com.sameer.exampay.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String examName;
    private int semester;
    private LocalDate examDate;

    public Exam() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExamName() { return examName; }
    public void setExamName(String examName) { this.examName = examName; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }
}