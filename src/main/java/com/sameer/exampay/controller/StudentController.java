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

        String hallTicket = loginRequest.getHallTicketNumber().trim().toUpperCase();
        String password = loginRequest.getPassword().trim();

        Student student = studentRepository
                .findByHallTicketNumberIgnoreCase(hallTicket);

        System.out.println("LOGIN INPUT: " + loginRequest.getHallTicketNumber() + " | " + loginRequest.getPassword());
        if(student == null){
            return ResponseEntity.status(401).body("Invalid Hall Ticket");
        }

        if(!student.getPassword().equals(password)){
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
    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student){

        // 🔥 Auto-generate password based on hallticket
        String hallTicket = student.getHallTicketNumber();

        if(hallTicket.endsWith("101") || hallTicket.endsWith("1")){
            student.setPassword("1234");
        } else if(hallTicket.endsWith("102") || hallTicket.endsWith("2")){
            student.setPassword("5678");
        } else if(hallTicket.endsWith("103") || hallTicket.endsWith("3")){
            student.setPassword("2468");
        } else if(hallTicket.endsWith("104") || hallTicket.endsWith("4")){
            student.setPassword("1357");
        } else if(hallTicket.endsWith("105") || hallTicket.endsWith("5")){
            student.setPassword("9999");
        } else {
            student.setPassword("0000"); // default fallback
        }

        // 1️⃣ Save student
        Student savedStudent = studentRepository.save(student);

        String[] semesters={
                "1-1","1-2",
                "2-1","2-2",
                "3-1","3-2",
                "4-1","4-2"
        };
        // 2️⃣ Create semester payments
        for(String sem : semesters){
            SemesterPayment p = new SemesterPayment();
            p.setHallTicketNumber(savedStudent.getHallTicketNumber());
            p.setSemester(sem);


// extract last digit
            char lastChar = hallTicket.charAt(hallTicket.length() - 1);
            int lastDigit = Character.getNumericValue(lastChar);

            double amount;

            if(lastDigit % 2 == 0){
                amount = 12000.0; // EVEN
            } else {
                amount = 8750.0; // ODD
            }

            p.setAmount(amount);
            p.setStatus("PENDING");

            semesterPaymentRepository.save(p);
        }

        return savedStudent;
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
