package com.sameer.exampay.repository;

import com.sameer.exampay.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
}