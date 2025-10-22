package ru.khamitova.NauJavaProject.repository;

import org.springframework.data.repository.CrudRepository;
import ru.khamitova.NauJavaProject.entity.Result;
import ru.khamitova.NauJavaProject.entity.Test;
import ru.khamitova.NauJavaProject.entity.User;

import java.util.List;

public interface ResultRepository extends CrudRepository<Result, Long> {
    List<Result> findByTestAndUser(Test test, User user);
}
