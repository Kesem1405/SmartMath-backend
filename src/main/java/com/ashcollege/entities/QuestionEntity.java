package com.ashcollege.entities;

import com.ashcollege.utils.Difficulty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class QuestionEntity extends BaseEntity{

    private String context;
    @Enumerated(EnumType.ORDINAL)
    private Difficulty difficulty;
    private TopicEntity topic;
    private QuestionTypeEntity questionType;

    public QuestionEntity(String context, Difficulty difficulty, TopicEntity topic, QuestionTypeEntity questionType) {
        this.context = context;
        this.difficulty = difficulty;
        this.topic = topic;
        this.questionType = questionType;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public TopicEntity getTopic() {
        return topic;
    }

    public void setTopic(TopicEntity topic) {
        this.topic = topic;
    }

    public QuestionTypeEntity getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionTypeEntity questionType) {
        this.questionType = questionType;
    }
}
