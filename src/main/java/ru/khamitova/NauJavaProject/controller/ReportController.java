package ru.khamitova.NauJavaProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.khamitova.NauJavaProject.service.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/m")
    public Long createReport(){
        Long id = reportService.createReport();
        reportService.generateReport(id);
        return id;
    }

    @GetMapping
    public String getTextById(@RequestParam Long id){
        return reportService.getText(id);
    }
}
