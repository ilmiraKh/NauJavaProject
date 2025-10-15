package ru.khamitova.NauJavaProject.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppInfoConfig {
    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @PostConstruct
    public void init() {
        System.out.println("Application Name: " + appName);
        System.out.println("Application Version: " + appVersion);
    }
}
