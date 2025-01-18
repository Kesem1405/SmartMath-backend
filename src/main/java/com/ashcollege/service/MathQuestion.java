package com.ashcollege.service;

public class MathQuestion {

    private String question;
    private int answer;

    public MathQuestion(String question, int answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public int getAnswer() {
        return answer;
    }
}
