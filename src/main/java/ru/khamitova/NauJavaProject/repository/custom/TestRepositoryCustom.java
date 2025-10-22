package ru.khamitova.NauJavaProject.repository.custom;

import ru.khamitova.NauJavaProject.entity.Test;

import java.util.List;

public interface TestRepositoryCustom {
    List<Test> findAllByTopicName(String topicName);
}
