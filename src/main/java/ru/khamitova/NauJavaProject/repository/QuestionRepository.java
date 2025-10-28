package ru.khamitova.NauJavaProject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.khamitova.NauJavaProject.entity.Question;

@RepositoryRestResource(path = "questions")
public interface QuestionRepository extends CrudRepository<Question, Long> {
}
