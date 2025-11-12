package ru.khamitova.NauJavaProject.service;

public interface ReportService {
    Long createReport();
    String getText(Long id);
    void generateReport(Long id);
}
