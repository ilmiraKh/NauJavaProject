package ru.khamitova.NauJavaProject.repository;

import org.springframework.data.repository.CrudRepository;
import ru.khamitova.NauJavaProject.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
