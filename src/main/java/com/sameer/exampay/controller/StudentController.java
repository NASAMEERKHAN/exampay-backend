package com.sameer.exampay.controller;

import com.sameer.exampay.entity.Student;
import com.sameer.exampay.entity.SemesterPayment;
import com.sameer.exampay.entity.Notification;

import com.sameer.exampay.repository.StudentRepository;
import com.sameer.exampay.repository.SemesterPaymentRepository;
import com.sameer.exampay.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*")
public class StudentController {
    @Autowired
    private StudentRepository repo;

    @GetMapping("/{dept}")
    public List<Student>getByDept(@PathVariable String dept){
        return repo.findByDeptOrderByIdAsc(dept);
    }

    private final StudentRepository studentRepository;
    private final SemesterPaymentRepository semesterPaymentRepository;
    private final NotificationRepository notificationRepository;

    public StudentController(StudentRepository studentRepository,
                             SemesterPaymentRepository semesterPaymentRepository,
                             NotificationRepository notificationRepository) {

        this.studentRepository = studentRepository;
        this.semesterPaymentRepository = semesterPaymentRepository;
        this.notificationRepository = notificationRepository;
    }

    // 🔐 LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Student loginRequest){

        Student student = studentRepository.findAll()
                .stream()
                .filter(s -> s.getHallTicketNumber() != null &&
                        s.getHallTicketNumber().trim()
                                .equalsIgnoreCase(loginRequest.getHallTicketNumber().trim()))
                .findFirst()
                .orElse(null);

        if(student == null){
            return ResponseEntity.status(401).body("Invalid Hall Ticket");
        }

        if(!student.getPassword().trim().equals(loginRequest.getPassword().trim())){
            return ResponseEntity.status(401).body("Invalid Password");
        }

        return ResponseEntity.ok(student);
    }

    // 📊 GET PAYMENTS
    @GetMapping("/payments/{hallTicket}")
    public List<SemesterPayment> getPayments(@PathVariable String hallTicket){

        return semesterPaymentRepository
                .findByHallTicketNumberOrderBySemesterAsc(hallTicket);
    }

    // 💰 MARK PAYMENT
    @PutMapping("/pay/{hallTicket}/{semester}")
    public String markPaid(@PathVariable String hallTicket,
                           @PathVariable String semester){

        SemesterPayment payment =
                semesterPaymentRepository
                        .findByHallTicketNumberAndSemester(hallTicket, semester);

        payment.setStatus("PAID");

        semesterPaymentRepository.save(payment);

        return "Payment updated successfully";
    }

    // 🔔 GET NOTIFICATIONS
    @GetMapping("/notifications/{studentId}")
    public List<Notification> getStudentNotifications(@PathVariable String studentId){

        return notificationRepository.findByReceiver(studentId)
                .stream()
                .filter(n ->!n.isHidden())
                .toList();
    }

    // 🔴 UNREAD COUNT
    @GetMapping("/notifications/unread/{studentId}")
    public long getUnreadCount(@PathVariable String studentId){

        return notificationRepository.findByReceiver(studentId)
                .stream()
                .filter(n -> !n.isRead())
                .count();
    }

    // ✔ MARK AS READ
    @PutMapping("/notifications/read/{id}")
    public String markAsRead(@PathVariable Long id) {

        Notification n = notificationRepository.findById(id).orElseThrow();

        n.setRead(true);
        notificationRepository.save(n);

        return "Marked as read";
    }
        @PutMapping("/notifications/delete/{id}")
        public String deleteNotification(@PathVariable Long id){

            Notification n = notificationRepository.findById(id).orElseThrow();

            n.setHidden(true); // 🔥 NOT deleting
            notificationRepository.save(n);

            return "Notification hidden";
        }

    @PutMapping("/notifications/read-all/{studentId}")
    public String markAllRead(@PathVariable String studentId){

        List<Notification> list =
                notificationRepository.findByReceiver(studentId);

        list.forEach(n -> n.setRead(true));

        notificationRepository.saveAll(list);

        return "All marked as read";
    }
    @PutMapping("/notifications/clear/{studentId}")
    public String clearAll(@PathVariable String studentId){

        List<Notification> list =
                notificationRepository.findByReceiver(studentId);

        list.forEach(n -> n.setHidden(true));

        notificationRepository.saveAll(list);

        return "All cleared (hidden)";
    }

    @PostMapping("/notify-hod")
    public String notifyHod(@RequestBody Notification n){
        notificationRepository.save(n);
        return "Notification sent";
    }

    @GetMapping("/hod-notifications/{dept}")
    public List<Notification> getHodNotifications(@PathVariable String dept){
        return notificationRepository.findByReceiver(dept)
                .stream()
                .filter(n->n.getType().equals("HOD"))
                .filter(n->n.getMessage().contains("paid"))
                .toList();
    }



    }
