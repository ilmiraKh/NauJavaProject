package ru.khamitova.NauJavaProject.service;

import ru.khamitova.NauJavaProject.entity.Question;
import ru.khamitova.NauJavaProject.entity.enums.QuestionType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface QuestionService {
    void createQuestion(String text, Integer points, QuestionType type, Map<String, Boolean> answers);
    List<Question> getAll();
    Optional<Question> getQuestionById(Long id);
    int answerQuestion(Long id, String userAnswer);
    int getTotalScore();
    boolean deleteQuestion(Long id);
    void editQuestionText(Long id, String newText);
}
