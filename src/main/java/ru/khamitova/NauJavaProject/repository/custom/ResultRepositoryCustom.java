package ru.khamitova.NauJavaProject.repository.custom;

import ru.khamitova.NauJavaProject.entity.Result;
import ru.khamitova.NauJavaProject.entity.Test;
import ru.khamitova.NauJavaProject.entity.User;

import java.util.List;

public interface ResultRepositoryCustom{
    List<Result> findByTestAndUser(Test test, User user);
}
