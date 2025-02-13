package com.ashcollege.controllers;

import com.ashcollege.entities.UserEntity;
import com.ashcollege.entities.UserProgressEntity;
import com.ashcollege.service.MathExerciseService;
import com.ashcollege.service.MathQuestion;
import com.ashcollege.service.Persist;
import com.ashcollege.utils.Difficulty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<String> register(@RequestBody Map<String, String> payload) {
        ResponseEntity<String> validationResponse = validateEmailAndPassword(payload);
        if (validationResponse != null) {
            return validationResponse;
        }

        String email = payload.get("email");
        String password = payload.get("password");

        if (persist.isEmailTaken(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already in use.");
        }

        persist.registerUser(email, password);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful!");
    }

    @PostMapping("/update-profile")
    public ResponseEntity<String> updateProfile(@RequestBody Map<String, String> payload) {
        ResponseEntity<String> validationResponse = validateEmailAndPassword(payload);
        if (validationResponse != null) {
            return validationResponse;
        }
        String email = payload.get("email");
        String password = payload.get("password");
        UserEntity user = persist.findUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        // Update the user's details
        user.setPassword(password);

        // Persist the updated user
        persist.save(user);

        return ResponseEntity.ok("Profile updated successfully!");
    }


    @GetMapping("/math/generate-question/{difficulty}")
    public ResponseEntity<?> generateQuestion(@PathVariable int difficulty) {
        try {
            if (difficulty < 1 || difficulty > 3) {
                throw new IllegalArgumentException("Difficulty must be between 1 and 3.");
            }
            UserProgressEntity userProgress = new UserProgressEntity();
            userProgress.setCurrentDifficulty(Difficulty.fromValue(difficulty));
            MathQuestion question = mathExerciseService.generateQuestion(userProgress);
            return ResponseEntity.ok(question);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception details
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while generating the question.");
        }
    }

    private ResponseEntity<String> validateEmailAndPassword(Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");

        if (!isValidEmail(email)) {
            return ResponseEntity.badRequest().body("Invalid email format.");
        }

        if (!isValidPassword(password)) {
            return ResponseEntity.badRequest().body("Password must be at least 8 characters long, contain a capital letter, and a special character.");
        }

        return null;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }
}
