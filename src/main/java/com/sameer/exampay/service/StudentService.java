package com.sameer.exampay.service;

import com.sameer.exampay.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    Student payFee(Long id);

}