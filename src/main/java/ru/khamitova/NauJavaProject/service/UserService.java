package ru.khamitova.NauJavaProject.service;

import ru.khamitova.NauJavaProject.entity.User;

public interface UserService {
    User findUserByUsername(String username);
    void addUser(User user);
}
