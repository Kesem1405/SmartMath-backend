package com.ashcollege.utils;

public enum QuestionTemplate {
    EASY("{num1} {operator} {num2} = ?"),
    MEDIUM("Solve for x: {equation}"),
    HARD("Find the derivative of {function}");

    private final String template;

    QuestionTemplate(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
}
