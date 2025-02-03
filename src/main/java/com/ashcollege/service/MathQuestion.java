package com.ashcollege.service;

import com.ashcollege.utils.Difficulty;

public class MathQuestion {
    private String question;
    private int answer;
    private Difficulty difficulty;  // Add difficulty field

    // Constructor, getters and setters
    public MathQuestion(String question, int answer, Difficulty difficulty) {
        this.question = question;
        this.answer = answer;
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public int getAnswer() {
        return answer;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
