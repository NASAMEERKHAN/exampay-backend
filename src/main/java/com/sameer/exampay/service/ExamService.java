package com.sameer.exampay.service;

import com.sameer.exampay.entity.Exam;
import java.util.List;

public interface ExamService {

    Exam saveExam(Exam exam);

    List<Exam> getAllExams();
}