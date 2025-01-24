package com.ashcollege.entities;

public class TopicEntity extends BaseEntity{
    private String name;

    public TopicEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
