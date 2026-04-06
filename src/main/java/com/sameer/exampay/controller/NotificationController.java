package com.sameer.exampay.controller;

import com.sameer.exampay.entity.Notification;
import com.sameer.exampay.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@CrossOrigin
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    // 🔥 HIDE NOTIFICATION API
    @PutMapping("/hide/{id}")
    public String hideNotification(@PathVariable Long id){

        Notification n = notificationRepository.findById(id).orElse(null);

        if(n == null){
            return "Notification NOT FOUND : " + id;
        }

        n.setHidden(true);
        notificationRepository.save(n);

        return "Hidden";
    }
}