package com.sameer.exampay.service;

import com.sameer.exampay.entity.Student;
import com.sameer.exampay.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student payFee(Long id){

        Student student = studentRepository.findById(id).orElseThrow();

        student.setPaid(true);

        String receipt = "REC-" + System.currentTimeMillis();



        return studentRepository.save(student);
    }
}