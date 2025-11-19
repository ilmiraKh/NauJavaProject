package ru.khamitova.NauJavaProject.entity;

import jakarta.persistence.*;
import ru.khamitova.NauJavaProject.entity.enums.ReportStatus;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String text;
    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }
}
