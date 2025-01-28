package com.ashcollege.utils;

public enum Difficulty {
    EASY(1),
    MEDIUM(2),
    HARD(3);

    private final int value;

    Difficulty(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Difficulty fromValue(int value) {
        for (Difficulty difficulty : Difficulty.values()) {
            if (difficulty.value == value) {
                return difficulty;
            }
        }
        throw new IllegalArgumentException("Invalid difficulty value: " + value);
    }
}