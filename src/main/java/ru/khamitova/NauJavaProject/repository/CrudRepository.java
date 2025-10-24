package ru.khamitova.NauJavaProject.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID>{
    void create(T entity);
    Optional<T> read(ID id);
    List<T> getAll();
    void update(T entity);
    void delete(ID id);
}
