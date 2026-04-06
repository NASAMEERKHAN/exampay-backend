package com.sameer.exampay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sameer.exampay.entity.Hod;
import com.sameer.exampay.repository.HodRepository;

@RestController
@RequestMapping("/hod")
@CrossOrigin
public class HodController {

    @Autowired
    private HodRepository repo;

    @PostMapping("/login")
    public Hod login(@RequestBody Hod hod) {

        return repo.findByUsernameAndPassword(
                hod.getUsername(),
                hod.getPassword()
        );
    }
}