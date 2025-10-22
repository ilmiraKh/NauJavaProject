package ru.khamitova.NauJavaProject.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import ru.khamitova.NauJavaProject.entity.Test;
import ru.khamitova.NauJavaProject.entity.Topic;
import ru.khamitova.NauJavaProject.repository.custom.TestRepositoryCustom;

import java.util.List;

@Repository
public class TestRepositoryImpl implements TestRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Test> findAllByTopicName(String topicName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Test> criteriaQuery = criteriaBuilder.createQuery(Test.class);
        Root<Test> root = criteriaQuery.from(Test.class);

        Join<Test, Topic> topicJoin = root.join("topic", JoinType.INNER);
        Predicate topicPredicate = criteriaBuilder.equal(topicJoin.get("name"), topicName);

        criteriaQuery.select(root).where(topicPredicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
