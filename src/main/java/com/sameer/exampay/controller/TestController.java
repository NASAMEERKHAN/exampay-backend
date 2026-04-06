package com.sameer.exampay.controller;

import com.sameer.exampay.entity.Semester;
import com.sameer.exampay.entity.Student;
import com.sameer.exampay.repository.SemesterRepository;
import com.sameer.exampay.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController {

    private final StudentRepository studentRepository;
    private final SemesterRepository semesterRepository;

    public TestController(StudentRepository studentRepository,
                          SemesterRepository semesterRepository) {
        this.studentRepository = studentRepository;
        this.semesterRepository = semesterRepository;
    }
    @PostMapping("/add-all-semesters")
    public String addSemestersToAll(){

        List<Student> students = studentRepository.findAll();

        for(Student student : students){

            Semester sem1 = new Semester();
            sem1.setSemester("4-1");
            sem1.setAmount(8750);
            sem1.setStatus("PAID");
            sem1.setStudent(student);

            Semester sem2 = new Semester();
            sem2.setSemester("4-2");
            sem2.setAmount(8750);
            sem2.setStatus("PENDING");
            sem2.setStudent(student);

            semesterRepository.save(sem1);
            semesterRepository.save(sem2);
        }

        return "Semesters added for all students!";
    }
}