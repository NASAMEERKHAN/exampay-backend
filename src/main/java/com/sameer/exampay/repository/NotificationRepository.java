package com.sameer.exampay.repository;

import com.sameer.exampay.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    boolean existsByReceiverAndSemester(String receiver,String semester);

    List<Notification> findByReceiver(String receiver);

}