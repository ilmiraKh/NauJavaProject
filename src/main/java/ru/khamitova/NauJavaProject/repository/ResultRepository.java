package ru.khamitova.NauJavaProject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.khamitova.NauJavaProject.entity.Result;
import ru.khamitova.NauJavaProject.entity.Test;
import ru.khamitova.NauJavaProject.entity.User;

import java.util.List;

@RepositoryRestResource(path = "results")
public interface ResultRepository extends CrudRepository<Result, Long> {
    List<Result> findByTestAndUser(Test test, User user);
}
