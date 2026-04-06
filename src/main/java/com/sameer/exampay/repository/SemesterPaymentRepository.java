package com.sameer.exampay.repository;

import com.sameer.exampay.entity.SemesterPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SemesterPaymentRepository extends JpaRepository<SemesterPayment, Long> {

    List<SemesterPayment> findByHallTicketNumberOrderBySemesterAsc(String hallTicketNumber);
    SemesterPayment findByHallTicketNumberAndSemester(
            String hallTicketNumber,
            String semester
    );
}