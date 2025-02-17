package com.ashcollege.service;

import com.ashcollege.entities.TopicEntity;
import com.ashcollege.entities.UserProgressEntity;
import com.ashcollege.utils.Difficulty;
import com.ashcollege.utils.QuestionTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MathExerciseService {

    private final Random random = new Random();

    public MathQuestion generateQuestion(UserProgressEntity userProgress) {
        Integer currentInARow = userProgress.getCurrentInARow();
        if (currentInARow == null) {
            currentInARow = 0;
        }
        if (currentInARow >= 3) {
            if (userProgress.getCurrentDifficulty() == Difficulty.EASY) {
                userProgress.setCurrentDifficulty(Difficulty.MEDIUM);
            } else if (userProgress.getCurrentDifficulty() == Difficulty.MEDIUM) {
                userProgress.setCurrentDifficulty(Difficulty.HARD);
            }
        }

        // Ensure difficulty is handled properly
        Difficulty currentDifficulty = userProgress.getCurrentDifficulty();
        if (currentDifficulty == null) {
            throw new IllegalArgumentException("Invalid difficulty level: " + currentDifficulty);
        }

        QuestionTemplate template = selectTemplate(userProgress.getCurrentTopic(), currentDifficulty);

        String question = generateQuestionFromTemplate(template, currentDifficulty);
        String solution = generateSolutionForQuestion(template, question);
        String answer = solution.split("=")[1].trim();  // Extract answer

        return new MathQuestion(question, answer, currentDifficulty);
    }

    private QuestionTemplate selectTemplate(TopicEntity topic, Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> QuestionTemplate.EASY;
            case MEDIUM -> QuestionTemplate.MEDIUM;
            case HARD -> QuestionTemplate.HARD;
            default -> throw new IllegalArgumentException("Unknown difficulty level");
        };
    }

    private String generateQuestionFromTemplate(QuestionTemplate template, Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return generateSimpleAdditionSubtractionQuestion(); // Only addition and subtraction
            case MEDIUM:
                return generateMultiplicationDivisionQuestion(); // Only multiplication and division
            case HARD:
                return generateLinearEquationQuestion(); // Linear equations
            default:
                throw new IllegalArgumentException("Unknown difficulty level");
        }
    }

    private String generateSimpleAdditionSubtractionQuestion() {
        int num1 = generateNumber(Difficulty.EASY);
        int num2 = generateNumber(Difficulty.EASY);
        String operator = random.nextBoolean() ? "+" : "-";  // Addition or subtraction
        return String.format("%d %s %d = ?", num1, operator, num2);
    }

    private String generateMultiplicationDivisionQuestion() {
        int num1 = generateNumber(Difficulty.MEDIUM);
        int num2 = generateNumber(Difficulty.MEDIUM);
        // Ensure the numbers are divisible by each other
        while (num2 == 0 || num1 % num2 != 0 || num1 > 15 || num2 > 15) {
            num2 = generateNumber(Difficulty.MEDIUM); // retry until divisible
        }
        String operator = random.nextBoolean() ? "*" : "/"; // Multiplication or division
        return String.format("%d %s %d = ?", num1, operator, num2);
    }

    private String generateLinearEquationQuestion() {
        int a = generateNumber(Difficulty.HARD);
        int b = generateNumber(Difficulty.HARD);
        int k = generateNumber(Difficulty.HARD); // Random integer for x
        int c = b + a * k; // Ensure (c - b) is divisible by a

        // Simple equation in the form ax + b = c
        return String.format("%dx + %d = %d", a, b, c);
    }

    private int generateNumber(Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return random.nextInt(10) + 1;  // Numbers between 1 and 10 for easy questions
            case MEDIUM:
                return random.nextInt(15) + 1; // Numbers between 1 and 15 for medium questions
            case HARD:
                return random.nextInt(15) + 1; // Numbers between 1 and 15 for hard questions (for linear equations)
            default:
                throw new IllegalArgumentException("Unknown difficulty level");
        }
    }

    private String generateSolutionForQuestion(QuestionTemplate template, String question) {
        switch (template) {
            case EASY:
                return solveAdditionSubtractionQuestion(question); // Solve addition/subtraction
            case MEDIUM:
                return solveMultiplicationDivisionQuestion(question); // Solve multiplication/division
            case HARD:
                return solveLinearEquationQuestion(question); // Solve linear equations
            default:
                throw new IllegalArgumentException("Unsupported template: " + template);
        }
    }

    private String solveAdditionSubtractionQuestion(String question) {
        String[] parts = question.split(" ");
        int num1 = Integer.parseInt(parts[0]);
        String operator = parts[1];
        int num2 = Integer.parseInt(parts[2]);

        int result;
        if (operator.equals("+")) {
            result = num1 + num2;
        } else if (operator.equals("-")) {
            result = num1 - num2;
        } else {
            throw new IllegalArgumentException("Unsupported operator: " + operator);
        }

        return String.format("%d %s %d = %d", num1, operator, num2, result);
    }

    private String solveMultiplicationDivisionQuestion(String question) {
        String[] parts = question.split(" ");
        int num1 = Integer.parseInt(parts[0]);
        String operator = parts[1];
        int num2 = Integer.parseInt(parts[2]);

        int result;
        if (operator.equals("*")) {
            result = num1 * num2;
        } else if (operator.equals("/")) {
            // Ensure division is handled properly and results in an integer
            if (num2 == 0) {
                throw new ArithmeticException("Division by zero is not allowed.");
            }
            // Use integer division to ensure the result is an integer
            result = num1 / num2;
        } else {
            throw new IllegalArgumentException("Unsupported operator: " + operator);
        }

        return String.format("%d %s %d = %d", num1, operator, num2, result);
    }

    private String solveLinearEquationQuestion(String question) {
        // Solve ax + b = c for x
        String equation = question.replace("x + ", "x+").replace("x - ", "x-"); // Handle spacing issues
        String[] parts = equation.split(" = ");

        // Split the left side into "ax + b" and parse "a", "b"
        String[] leftParts = parts[0].split("x");

        // Parse the values for a, b, and c
        int a = Integer.parseInt(leftParts[0].trim()); // a (coefficient of x)

        String bPart = leftParts.length > 1 ? leftParts[1].trim() : "0";
        int b = bPart.equals("") ? 0 : Integer.parseInt(bPart); // default to 0 if no "b" value

        int c = Integer.parseInt(parts[1].trim()); // c (right side of equation)

        // Ensure the result is an integer by checking divisibility
        int result = c - b;
        if (result % a != 0) {
            // Skip this equation generation if the result is not divisible evenly
            throw new ArithmeticException("The equation does not result in an integer solution.");
        }

        int x = result / a;  // Solving for x
        return String.format("x = %d", x);
    }
}