package ru.khamitova.NauJavaProject.entity;

import ru.khamitova.NauJavaProject.entity.enums.QuestionType;

import java.util.Map;

public class Question {
    private Long id;
    private String text;
    private Integer points;
    private QuestionType type;
    private Map<String, Boolean> answers;
    // в задании сказано реализовать только 1 сущность, поэтому пока ответы храню так

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public Map<String, Boolean> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, Boolean> answers) {
        this.answers = answers;
    }
}
