package ru.khamitova.NauJavaProject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.khamitova.NauJavaProject.entity.Test;

import java.util.List;

@RepositoryRestResource(path = "tests")
public interface TestRepository extends CrudRepository<Test, Long> {
    @Query("SELECT t FROM Test t WHERE t.topic.name = :topicName")
    List<Test> findAllByTopicName(@Param("topicName") String topicName);
}
