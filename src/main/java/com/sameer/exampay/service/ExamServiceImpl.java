package com.sameer.exampay.service;

import com.sameer.exampay.entity.Exam;
import com.sameer.exampay.repository.ExamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;

    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    @Override
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }
}