package com.sameer.exampay.repository;


import com.sameer.exampay.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    
    Student findByHallTicketNumberIgnoreCase(String hallTicketNumber);

    List<Student> findByDept(String dept);

    List<Student> findByDeptOrderByIdAsc(String dept);
}
