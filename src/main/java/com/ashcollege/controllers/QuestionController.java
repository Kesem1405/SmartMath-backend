package com.ashcollege.controllers;

import com.ashcollege.entities.UserProgressEntity;
import com.ashcollege.service.MathExerciseService;
import com.ashcollege.service.MathQuestion;
import com.ashcollege.utils.Difficulty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private final MathExerciseService mathExerciseService;

    @Autowired
    public QuestionController(MathExerciseService mathExerciseService) {
        this.mathExerciseService = mathExerciseService;
    }

    @GetMapping("/generate/{difficulty}")
    public ResponseEntity<?> generateQuestion(@PathVariable("difficulty") String difficultyValue) {
        try {
            Difficulty difficulty;
            try {
                difficulty = Difficulty.valueOf(difficultyValue.toUpperCase()); // For EASY, MEDIUM, HARD
            } catch (IllegalArgumentException e) {
                int numericValue = Integer.parseInt(difficultyValue); // For 1, 2, 3
                difficulty = Difficulty.fromValue(numericValue);
            }

            UserProgressEntity userProgress = fetchUserProgress();
            userProgress.setCurrentDifficulty(difficulty);

            MathQuestion question = mathExerciseService.generateQuestion(userProgress);
            return ResponseEntity.ok(question);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid difficulty level: " + difficultyValue);
        }
    }


    // Example method to mock or fetch UserProgressEntity
    private UserProgressEntity fetchUserProgress() {
        UserProgressEntity userProgress = new UserProgressEntity();
        userProgress.setCurrentDifficulty(Difficulty.EASY);
        userProgress.setScoreForDifficulty(100);
        userProgress.setCurrentInARow(3);
        return userProgress;
    }


}
