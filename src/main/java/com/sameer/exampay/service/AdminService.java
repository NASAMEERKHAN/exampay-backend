package com.sameer.exampay.service;

import com.sameer.exampay.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.ArrayList;

import com.sameer.exampay.repository.StudentRepository;
import com.sameer.exampay.dto.AdminDTO;

@Service
public class AdminService {

    @Autowired
    private StudentRepository studentRepository;

    public List<AdminDTO> getAllStudentPayments(){

        List<Student> students = studentRepository.findAll();
        List<AdminDTO> result = new ArrayList<>();

        for(Student s : students){

            AdminDTO dto = new AdminDTO();

            dto.setStudentId(s.getHallTicketNumber());

            // TEMP FIX
            dto.setDepartment("CSE");

            dto.setStatus(s.getApprovalStatus());

            dto.setSemesters(new ArrayList<>()); // empty for now

            result.add(dto);
        }

        return result;
    }
}