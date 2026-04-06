package com.sameer.exampay.repository;

import com.sameer.exampay.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByStudentIdAndExamId(Long studentId, Long examId);
}