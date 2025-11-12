package ru.khamitova.NauJavaProject.repository;

import org.springframework.data.repository.CrudRepository;
import ru.khamitova.NauJavaProject.entity.Report;

public interface ReportRepository extends CrudRepository<Report, Long> {
}
