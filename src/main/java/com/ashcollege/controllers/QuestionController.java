package com.ashcollege.controllers;

import com.ashcollege.entities.TopicEntity;
import com.ashcollege.entities.UserProgressEntity;
import com.ashcollege.service.MathExerciseService;
import com.ashcollege.service.MathQuestion;
import com.ashcollege.utils.Difficulty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> generateQuestion(@PathVariable String difficulty) {
        try {
            // Log the raw received difficulty
            System.out.println("Raw received difficulty: " + difficulty);

            // Convert difficulty to uppercase to ensure correct enum mapping
            Difficulty difficultyEnum = Difficulty.valueOf(difficulty.toUpperCase());

            // Log the mapped enum value
            System.out.println("Mapped difficulty: " + difficultyEnum);

            UserProgressEntity userProgress = new UserProgressEntity();
            userProgress.setCurrentDifficulty(difficultyEnum);
            userProgress.setCurrentTopic(new TopicEntity(difficultyEnum.name()));
            MathQuestion question = mathExerciseService.generateQuestion(userProgress);
            return ResponseEntity.ok(question);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid difficulty level: " + difficulty);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while generating the question.");
        }
    }





    private UserProgressEntity fetchUserProgress() {
        UserProgressEntity userProgress = new UserProgressEntity();
        userProgress.setCurrentDifficulty(Difficulty.EASY);
        userProgress.setScoreForDifficulty(100);
        userProgress.setCurrentInARow(3);
        return userProgress;
    }


}
