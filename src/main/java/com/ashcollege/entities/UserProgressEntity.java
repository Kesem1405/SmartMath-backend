package com.ashcollege.entities;

import com.ashcollege.utils.Difficulty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class UserProgressEntity extends BaseEntity{

    private UserEntity user;
    private QuestionEntity currentQuestion;
    @Enumerated(EnumType.ORDINAL)
    private Difficulty currentDifficulty;
    private TopicEntity currentTopic;
    private Integer currentInARow; //כמות התשובות הנכונות שענה **ברצף
    private Boolean isInIntermediateLevel; //האם נמצא בשלב ביניים
    private Integer scoreForDifficulty; //סופר ניקוד לכל רמת קושי, שעולים רמה מתאפס


    public UserProgressEntity() {

    }

    public UserProgressEntity(UserEntity user, Difficulty currentDifficulty, TopicEntity currentTopic, Integer currentInARow, Boolean isInIntermediateLevel, Integer score) {
        this.user = user;
        this.currentDifficulty = currentDifficulty;
        this.currentTopic = currentTopic;
        this.currentInARow = currentInARow;
        this.isInIntermediateLevel = isInIntermediateLevel;
        this.scoreForDifficulty = score;
    }


    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public QuestionEntity getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(QuestionEntity currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public Difficulty getCurrentDifficulty() {
        return currentDifficulty;
    }

    public void setCurrentDifficulty(Difficulty currentDifficulty) {
        this.currentDifficulty = currentDifficulty;
    }

    public TopicEntity getCurrentTopic() {
        return currentTopic;
    }

    public void setCurrentTopic(TopicEntity currentTopic) {
        this.currentTopic = currentTopic;
    }

    public Integer getCurrentInARow() {
        return currentInARow;
    }

    public void setCurrentInARow(Integer currentInARow) {
        this.currentInARow = currentInARow;
    }

    public Boolean getIsInIntermediateLevel() {
        return isInIntermediateLevel;
    }

    public void setIsInIntermediateLevel(Boolean inIntermediateLevel) {
        isInIntermediateLevel = inIntermediateLevel;
    }


    public Integer getScoreForDifficulty() {
        return scoreForDifficulty;
    }

    public void setScoreForDifficulty(Integer scoreForDifficulty) {
        this.scoreForDifficulty = scoreForDifficulty;
    }
}
