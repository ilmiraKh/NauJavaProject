package ru.khamitova.NauJavaProject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.khamitova.NauJavaProject.entity.Answer;
import ru.khamitova.NauJavaProject.entity.Question;

import java.util.List;

@RepositoryRestResource(path = "answers")
public interface AnswerRepository extends CrudRepository<Answer, Long> {
    // метод для п.7 - транзакции
    List<Answer> findByQuestion(Question question);
}
