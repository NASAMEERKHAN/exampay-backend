package com.sameer.exampay.controller;

import com.sameer.exampay.entity.Exam;
import com.sameer.exampay.service.ExamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public Exam addExam(@RequestBody Exam exam) {
        return examService.saveExam(exam);
    }

    @GetMapping
    public List<Exam> getAllExams() {
        return examService.getAllExams();
    }
}