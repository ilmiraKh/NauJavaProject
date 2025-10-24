package ru.khamitova.NauJavaProject.repository;

import org.springframework.data.repository.CrudRepository;
import ru.khamitova.NauJavaProject.entity.Answer;
import ru.khamitova.NauJavaProject.entity.Question;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    // метод для п.7 - транзакции
    List<Answer> findByQuestion(Question question);
}
