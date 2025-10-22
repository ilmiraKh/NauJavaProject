package ru.khamitova.NauJavaProject.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import ru.khamitova.NauJavaProject.entity.Result;
import ru.khamitova.NauJavaProject.entity.Test;
import ru.khamitova.NauJavaProject.entity.User;
import ru.khamitova.NauJavaProject.repository.custom.ResultRepositoryCustom;

import java.util.List;

@Repository
public class ResultRepositoryImpl implements ResultRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Result> findByTestAndUser(Test test, User user) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Result> criteriaQuery = criteriaBuilder.createQuery(Result.class);
        Root<Result> root = criteriaQuery.from(Result.class);
        Predicate predicateTest = criteriaBuilder.equal(root.get("test"), test);
        Predicate predicateUser = criteriaBuilder.equal(root.get("user"), user);

        criteriaQuery.select(root).where(criteriaBuilder.and(predicateTest, predicateUser));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
