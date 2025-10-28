package ru.khamitova.NauJavaProject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.khamitova.NauJavaProject.entity.Topic;

@RepositoryRestResource(path = "topics")
public interface TopicRepository extends CrudRepository<Topic, Long> {
}
