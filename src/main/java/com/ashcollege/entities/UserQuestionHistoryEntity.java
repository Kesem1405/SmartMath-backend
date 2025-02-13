package com.ashcollege.entities;

import java.time.LocalDateTime;
import java.util.Date;

public class UserQuestionHistoryEntity extends BaseEntity{
    private UserEntity user;
    private QuestionEntity question;
    private Boolean isAnsweredCorrectly;
    private LocalDateTime questionSendTime;
    private Integer attemptNum; //מס הניסיונות שענה על השאלה
    private LocalDateTime answerTime;

    public UserQuestionHistoryEntity(UserEntity user, QuestionEntity question, Boolean isAnsweredCorrectly, LocalDateTime questionSendTime, Integer attemptNum, LocalDateTime answerTime) {
        this.user = user;
        this.question = question;
        this.isAnsweredCorrectly = isAnsweredCorrectly;
        this.questionSendTime = questionSendTime;
        this.attemptNum = attemptNum;
        this.answerTime = answerTime;
    }

    public UserQuestionHistoryEntity() {
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    public Boolean getIsAnsweredCorrectly() {
        return isAnsweredCorrectly;
    }

    public void setIsAnsweredCorrectly(Boolean answeredCorrectly) {
        isAnsweredCorrectly = answeredCorrectly;
    }

    public LocalDateTime getQuestionSendTime() {
        return questionSendTime;
    }

    public void setQuestionSendTime(LocalDateTime questionSendTime) {
        this.questionSendTime = questionSendTime;
    }

    public Integer getAttemptNum() {
        return attemptNum;
    }

    public void setAttemptNum(Integer attemptNum) {
        this.attemptNum = attemptNum;
    }

    public LocalDateTime getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(LocalDateTime answerTime) {
        this.answerTime = answerTime;
    }
}
