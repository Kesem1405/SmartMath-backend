package com.ashcollege.controllers;

import com.ashcollege.entities.UserEntity;
import com.ashcollege.service.MathExerciseService;
import com.ashcollege.service.MathQuestion;
import com.ashcollege.service.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Persist persist;
    private final MathExerciseService mathExerciseService;

    @Autowired
    public UserController(Persist persist, MathExerciseService mathExerciseService) {
        this.persist = persist;
        this.mathExerciseService = mathExerciseService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        if (!email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            return ResponseEntity.badRequest().body("Invalid email format.");
        }

        if (password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            return ResponseEntity.badRequest().body("Password must be at least 8 characters long, contain a capital letter, and a special character.");
        }

        if (persist.isEmailTaken(email)) {
            return ResponseEntity.badRequest().body("Email is already in use.");
        }

        persist.registerUser(email, password);
        return ResponseEntity.ok("Registration successful!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        UserEntity user = persist.loginUser(email, password);
        if (user != null) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.badRequest().body("Invalid email or password.");
        }
    }

    @GetMapping("/math/generate-question/{difficulty}")
    public ResponseEntity<MathQuestion> generateQuestion(@PathVariable int difficulty) {
        try {
            MathQuestion question = mathExerciseService.generateQuestion(difficulty);
            return ResponseEntity.ok(question);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
