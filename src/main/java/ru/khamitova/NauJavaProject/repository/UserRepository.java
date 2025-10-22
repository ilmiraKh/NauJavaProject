package ru.khamitova.NauJavaProject.repository;

import org.springframework.data.repository.CrudRepository;
import ru.khamitova.NauJavaProject.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
