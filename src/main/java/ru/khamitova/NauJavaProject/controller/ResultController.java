package ru.khamitova.NauJavaProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.khamitova.NauJavaProject.entity.Result;
import ru.khamitova.NauJavaProject.entity.Test;
import ru.khamitova.NauJavaProject.entity.User;
import ru.khamitova.NauJavaProject.repository.TestRepository;
import ru.khamitova.NauJavaProject.repository.UserRepository;
import ru.khamitova.NauJavaProject.repository.custom.ResultRepositoryCustom;

import java.util.List;

@RestController
@RequestMapping("/custom/results")
public class ResultController {
    private final ResultRepositoryCustom resultRepository;
    private final TestRepository testRepository;
    private final UserRepository userRepository;

    @Autowired
    public ResultController(ResultRepositoryCustom resultRepository, TestRepository testRepository, UserRepository userRepository) {
        this.resultRepository = resultRepository;
        this.testRepository = testRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("findByTestAndUser")
    public List<Result> findByTestAndUser(@RequestParam Long testId, @RequestParam Long userId){

        Test test = testRepository.findById(testId).orElseThrow(() -> new ResourceNotFoundException("Test not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return resultRepository.findByTestAndUser(test, user);
        /*
        у result есть поля - внешние ключи (как раз user и test), ни загружаются отложенно,
        и при сериализации jackson не может обработать hibernateProxy :(
        поэтому я добавила jacksonConfig, где регистрирую HibernateModule
         */
    }
}
