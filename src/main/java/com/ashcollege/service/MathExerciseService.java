package com.ashcollege.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MathExerciseService {

    public MathQuestion generateQuestion(int difficulty) {
        Random random = new Random();
        int num1, num2;
        String operation = random.nextBoolean() ? "+" : "-";

        if (difficulty == 1) {
            num1 = random.nextInt(10) + 1;  // בין 1 ל-10
            num2 = random.nextInt(10) + 1;  // בין 1 ל-10
        } else if (difficulty == 2) {
            num1 = random.nextInt(40) + 10; // בין 10 ל-50
            num2 = random.nextInt(40) + 10; // בין 10 ל-50
        } else if (difficulty == 3) {
            num1 = random.nextInt(50) + 50; // בין 50 ל-100
            num2 = random.nextInt(50) + 50; // בין 50 ל-100
        } else {
            throw new IllegalArgumentException("Invalid difficulty level");
        }

        if (operation.equals("-") && num1 < num2) {
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }

        String question = num1 + " " + operation + " " + num2;
        int answer = operation.equals("+") ? num1 + num2 : num1 - num2;

        return new MathQuestion(question, answer);
    }
}
