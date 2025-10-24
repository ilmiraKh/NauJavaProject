package ru.khamitova.NauJavaProject.service.impl;

import org.springframework.stereotype.Service;
import ru.khamitova.NauJavaProject.entity.Question;
import ru.khamitova.NauJavaProject.entity.enums.QuestionType;
import ru.khamitova.NauJavaProject.repository.CrudRepository;
import ru.khamitova.NauJavaProject.service.QuestionService;

import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService {
    private CrudRepository<Question, Long> questionRepository;
    private int totalScore = 0;

    public QuestionServiceImpl(CrudRepository<Question, Long> questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void createQuestion(String text, Integer points, QuestionType type, Map<String, Boolean> answers){
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Question text can't be empty");
        }
        if (points == null || points <= 0) {
            throw new IllegalArgumentException("Points must be positive number");
        }

        if (type == null){
            throw new IllegalArgumentException("Question type can't be null");
        }

        if (answers == null || answers.isEmpty()) {
            throw new IllegalArgumentException("Your question should have correct answer");
        }

        Question question = new Question();
        question.setText(text);
        question.setPoints(points);
        question.setType(type);
        question.setAnswers(answers);

        questionRepository.create(question);
    }

    @Override
    public List<Question> getAll() {
        return questionRepository.getAll();
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.read(id);
    }

    @Override
    public int answerQuestion(Long id, String userAnswer) {
        if (userAnswer == null || userAnswer.isBlank()) {
            throw new IllegalArgumentException("Answer can't be empty");
        }

        Optional<Question> questionOpt = questionRepository.read(id);
        if (questionOpt.isEmpty()) {
            throw new IllegalArgumentException("Question with id " + id + " not found");
        }

        Question question = questionOpt.get();
        Map<String, Boolean> answers = question.getAnswers();
        userAnswer = userAnswer.trim();

        switch (question.getType()) {
            case CHOICE -> {
                for (Map.Entry<String, Boolean> entry : answers.entrySet()) {
                    if (entry.getKey().trim().equalsIgnoreCase(userAnswer)) {
                        if (entry.getValue()) {
                            this.totalScore += question.getPoints();
                            return question.getPoints();
                        }
                    }
                }
            }
            case OPEN -> {
                for (String correct : answers.keySet()) {
                    if (correct.trim().equalsIgnoreCase(userAnswer)) {
                        this.totalScore += question.getPoints();
                        return question.getPoints();
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public int getTotalScore() {
        return totalScore;
    }

    @Override
    public boolean deleteQuestion(Long id) {
        Optional<Question> existing = questionRepository.read(id);
        if (existing.isEmpty()) {
            return false;
        }
        questionRepository.delete(id);
        return true;
    }

    @Override
    public void editQuestionText(Long id, String newText) {
        if (id == null) {
            throw new IllegalArgumentException("Id can't be null");
        }
        if (newText == null || newText.isBlank()) {
            throw new IllegalArgumentException("Question text can't be empty");
        }

        Optional<Question> questionOpt = questionRepository.read(id);
        if (questionOpt.isEmpty()) {
            throw new IllegalArgumentException("Question with id " + id + " not found");
        }

        Question question = questionOpt.get();
        question.setText(newText);
        questionRepository.update(question);
    }
}
