package com.sameer.exampay.controller;

import com.sameer.exampay.dto.LoginRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {

        if ("admin".equals(request.getUsername()) &&
                "admin123".equals(request.getPassword())) {

            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("role", "ADMIN");

            return response;
        }

        throw new RuntimeException("Invalid username or password");
    }
}