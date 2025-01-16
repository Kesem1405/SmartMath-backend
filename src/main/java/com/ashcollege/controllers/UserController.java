package com.ashcollege.controllers;

import com.ashcollege.entities.UserEntity;
import com.ashcollege.service.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Persist persist;

    @Autowired
    public UserController(Persist persist) {
        this.persist = persist;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam("email") String email, // Explicitly specify the parameter name
            @RequestParam("password") String password // Explicitly specify the parameter name
    ) {
        // Validate email format
        if (!email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            return ResponseEntity.badRequest().body("Invalid email format.");
        }

        // Validate password criteria
        if (password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            return ResponseEntity.badRequest().body("Password must be at least 8 characters long, contain a capital letter, and a special character.");
        }

        // Check if email already exists
        if (persist.isEmailTaken(email)) {
            return ResponseEntity.badRequest().body("Email is already in use.");
        }

        // Register the user
        persist.registerUser(email, password);
        return ResponseEntity.ok("Registration successful!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam("email") String email, // Explicitly specify the parameter name
            @RequestParam("password") String password // Explicitly specify the parameter name
    ) {
        UserEntity user = persist.loginUser(email, password);
        if (user != null) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.badRequest().body("Invalid email or password.");
        }
    }
}