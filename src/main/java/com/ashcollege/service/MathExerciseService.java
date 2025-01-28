package com.ashcollege.service;

import com.ashcollege.entities.UserProgressEntity;
import com.ashcollege.utils.Difficulty;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MathExerciseService {

    public MathQuestion generateQuestion(UserProgressEntity userProgress) {
        Random random = new Random();
        int num1, num2;
        String operation = random.nextBoolean() ? "+" : "-";
        Difficulty currentDifficulty = userProgress.getCurrentDifficulty();
        Integer score = userProgress.getScoreForDifficulty();
        Integer currentInARow = userProgress.getCurrentInARow();

        // Adjust difficulty based on user metrics
        if (score >= 1000 && currentInARow >= 5 ) {
            currentDifficulty = Difficulty.HARD;
        } else if (score >= 500 && currentInARow >= 3) {
            currentDifficulty = Difficulty.MEDIUM;
        }

        // Generate numbers based on difficulty
        switch (currentDifficulty) {
            case EASY:
                num1 = random.nextInt(10) + 1;
                num2 = random.nextInt(10) + 1;
                break;
            case MEDIUM:
                num1 = random.nextInt(40) + 10;
                num2 = random.nextInt(40) + 10;
                break;
            case HARD:
                num1 = random.nextInt(50) + 50;
                num2 = random.nextInt(50) + 50;
                break;
            default:
                throw new IllegalArgumentException("Unknown difficulty level");
        }

        if (operation.equals("-") && num1 < num2) {
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }

        String question = num1 + " " + operation + " " + num2;
        int answer = operation.equals("+") ? num1 + num2 : num1 - num2;

        // Update the user progress if necessary
        updateUserProgress(userProgress, currentDifficulty, score, currentInARow);

        return new MathQuestion(question, answer);
    }

    private void updateUserProgress(UserProgressEntity userProgress, Difficulty newDifficulty, Integer score, Integer currentInARow) {
        if (newDifficulty != userProgress.getCurrentDifficulty()) {
            userProgress.setCurrentDifficulty(newDifficulty);
            userProgress.setScoreForDifficulty(0);  // Reset score for new difficulty
        }
        userProgress.setScoreForDifficulty(score);
        userProgress.setCurrentInARow(currentInARow);
    }

}
