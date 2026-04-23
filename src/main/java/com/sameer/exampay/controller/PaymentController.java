package com.sameer.exampay.controller;

import com.sameer.exampay.entity.Payment;
import com.sameer.exampay.entity.Exam;
import com.sameer.exampay.entity.Student;
import com.sameer.exampay.repository.PaymentRepository;
import com.sameer.exampay.repository.StudentRepository;
import com.sameer.exampay.repository.ExamRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;

    public PaymentController(PaymentRepository paymentRepository,
                             StudentRepository studentRepository,
                             ExamRepository examRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
        this.examRepository = examRepository;
    }


    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {

        Student student = studentRepository
                .findById(payment.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Exam exam = examRepository
                .findById(payment.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        payment.setPaymentStatus("PAID");

        return paymentRepository.save(payment);
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
