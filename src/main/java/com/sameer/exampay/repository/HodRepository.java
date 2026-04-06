package com.sameer.exampay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sameer.exampay.entity.Hod;

public interface HodRepository extends JpaRepository<Hod, String> {

    Hod findByUsernameAndPassword(String username, String password);

}