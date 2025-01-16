package com.ashcollege.controllers;

import com.ashcollege.entities.UserEntity;
import com.ashcollege.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String password) {
        if (userService.registerUser(email, password)) {
            return "Registration successful!";
        } else {
            return "Email already in use.";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        UserEntity user = userService.loginUser(email, password);
        if (user != null) {
            return "Login successful!";
        } else {
            return "Invalid email or password.";
        }
    }
}