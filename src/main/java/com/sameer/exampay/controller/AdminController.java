package com.sameer.exampay.controller;

import com.sameer.exampay.dto.AdminDTO;
import com.sameer.exampay.dto.SemesterDTO;
import com.sameer.exampay.entity.Notification;
import com.sameer.exampay.entity.Student;
import com.sameer.exampay.entity.SemesterPayment;

import com.sameer.exampay.repository.NotificationRepository;
import com.sameer.exampay.repository.StudentRepository;
import com.sameer.exampay.repository.SemesterPaymentRepository;

import java.util.HashMap;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final StudentRepository studentRepository;
    private final NotificationRepository notificationRepository;
    private final SemesterPaymentRepository semesterPaymentRepository;

    public AdminController(StudentRepository studentRepository,
                           NotificationRepository notificationRepository,
                           SemesterPaymentRepository semesterPaymentRepository) {

        this.studentRepository = studentRepository;
        this.notificationRepository = notificationRepository;
        this.semesterPaymentRepository = semesterPaymentRepository;
    }

    @GetMapping("/analytics")
    public Map<String, Integer> getAnalytics(){

        List<SemesterPayment> payments =
                semesterPaymentRepository.findAll();

        int paid = 0;
        int pending = 0;

        for(SemesterPayment p : payments){
            if(p.getStatus().equalsIgnoreCase("PAID")){
                paid++;
            } else {
                pending++;
            }
        }

        Map<String, Integer> data = new HashMap<>();
        data.put("paid", paid);
        data.put("pending", pending);
        data.put("total", payments.size());

        return data;
    }


    // 🔥 GET ALL STUDENTS + THEIR PAYMENT DATA
    @GetMapping("/payments")
    public List<AdminDTO> getAllPayments(){

        List<Student> students = studentRepository.findAll();
        List<AdminDTO> result = new ArrayList<>();

        for(Student s : students){

            AdminDTO dto = new AdminDTO();

            dto.setStudentId(s.getHallTicketNumber());

            dto.setDepartment(s.getDept());


            List<SemesterDTO> semList = new ArrayList<>();



            // 🔥 GET DATA FROM SemesterPayment TABLE (IMPORTANT FIX)
            List<SemesterPayment> payments =
                    semesterPaymentRepository
                            .findByHallTicketNumberOrderBySemesterAsc(
                                    s.getHallTicketNumber()
                            );

            for(SemesterPayment p : payments){

                SemesterDTO sd = new SemesterDTO();
                sd.setSemester(p.getSemester());
                sd.setAmount(p.getAmount());
                sd.setStatus(p.getStatus());

                semList.add(sd);
            }

            dto.setSemesters(semList);

            // 🔥 overall status
            boolean allPaid = semList.stream()
                    .allMatch(x -> x.getStatus().equalsIgnoreCase("PAID"));

            dto.setStatus(allPaid ? "PAID" : "PENDING");

            result.add(dto);
        }

        return result;
    }

    // 🔔 NOTIFY STUDENT
    @PostMapping("/notify-student/{studentId}/{semester}")
    public String notifyStudent(@PathVariable String studentId,
                                @PathVariable String semester){

        Notification n = new Notification();

        n.setReceiver(studentId);
        n.setType("STUDENT");
        n.setMessage(
                "Reminder: Pay your " + semester + " exam fee before deadline to avoid penalty."
        );
        n.setRead(false);
        n.setHidden(false);
        n.setCreatedAt(
                java.time.LocalDateTime.now()
                        .toString()
        );

        notificationRepository.save(n);

        return "Student notified";
    }

    @PostMapping("/notify-year/{year}")
    public String notifyYear(@PathVariable int year){

        List<Student> students = studentRepository.findAll();

        List<String> semesters = new ArrayList<>();

        if(year == 1) semesters = List.of("1-1","1-2");
        if(year == 2) semesters = List.of("2-1","2-2");
        if(year == 3) semesters = List.of("3-1","3-2");
        if(year == 4) semesters = List.of("4-1","4-2");

        for(Student s : students){

            List<SemesterPayment> payments =
                    semesterPaymentRepository
                            .findByHallTicketNumberOrderBySemesterAsc(
                                    s.getHallTicketNumber()
                            );

            for(SemesterPayment p : payments){

                if(semesters.contains(p.getSemester())
                        && p.getStatus().equalsIgnoreCase("PENDING")){

                    boolean alreadySent =
                            notificationRepository.existsByReceiverAndSemester(
                                    s.getHallTicketNumber(),
                                    p.getSemester()
                            );

                    if(!alreadySent){

                        Notification n = new Notification();

                        n.setReceiver(s.getHallTicketNumber());
                        n.setType("STUDENT");

                        n.setSemester(p.getSemester()); // 🔥 IMPORTANT

                        n.setMessage(
                                "Reminder: Pay your " + p.getSemester()
                                        + " exam fee before deadline to avoid penalty."
                        );

                        n.setRead(false);
                        n.setHidden(false);
                        n.setCreatedAt(java.time.LocalDateTime.now().toString());

                        notificationRepository.save(n);
                    }
                }
            }
        }

        return "Notifications sent (only unpaid & no duplicates)";
    }

    // 🔔 NOTIFY HOD
    @PostMapping("/notify-hod/{department}")
    public String notifyHod(@PathVariable String department){

        Notification n = new Notification();

        n.setReceiver(department);
        n.setType("HOD");
        n.setMessage("Student has paid fee. Issue hall ticket.");
        n.setRead(false);
        n.setCreatedAt(java.time.LocalDateTime.now().toString());

        notificationRepository.save(n);

        return "HOD notified";
    }
}