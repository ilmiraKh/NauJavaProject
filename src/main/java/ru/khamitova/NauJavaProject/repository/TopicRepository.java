package ru.khamitova.NauJavaProject.repository;

import org.springframework.data.repository.CrudRepository;
import ru.khamitova.NauJavaProject.entity.Topic;

public interface TopicRepository extends CrudRepository<Topic, Long> {
}
