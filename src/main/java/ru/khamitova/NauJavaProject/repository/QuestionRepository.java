package ru.khamitova.NauJavaProject.repository;

import org.springframework.data.repository.CrudRepository;
import ru.khamitova.NauJavaProject.entity.Question;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}
