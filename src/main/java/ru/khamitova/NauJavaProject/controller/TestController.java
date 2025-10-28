package ru.khamitova.NauJavaProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.khamitova.NauJavaProject.entity.Test;
import ru.khamitova.NauJavaProject.repository.custom.TestRepositoryCustom;

import java.util.List;

@RestController
@RequestMapping("/custom/tests")
public class TestController {
    private final TestRepositoryCustom testRepository;

    @Autowired
    public TestController(TestRepositoryCustom testRepository) {
        this.testRepository = testRepository;
    }

    @GetMapping("/findByTopicName")
    public List<Test> findAllByTopicName(@RequestParam String topicName){
        return testRepository.findAllByTopicName(topicName);
    }
}
