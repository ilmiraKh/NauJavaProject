package ru.khamitova.NauJavaProject;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.khamitova.NauJavaProject.entity.Result;
import ru.khamitova.NauJavaProject.entity.Test;
import ru.khamitova.NauJavaProject.entity.Topic;
import ru.khamitova.NauJavaProject.entity.User;
import ru.khamitova.NauJavaProject.entity.enums.Role;
import ru.khamitova.NauJavaProject.repository.ResultRepository;
import ru.khamitova.NauJavaProject.repository.TestRepository;
import ru.khamitova.NauJavaProject.repository.TopicRepository;
import ru.khamitova.NauJavaProject.repository.UserRepository;
import ru.khamitova.NauJavaProject.repository.custom.ResultRepositoryCustom;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ResultRepositoryTest {
    // тестирую методы для обоих репозиториев Result - из п.5 (Query) и п.6 (Criteria API)
    private final ResultRepository resultRepository;
    private final ResultRepositoryCustom resultRepositoryCustom;

    private final UserRepository userRepository;
    private final TestRepository testRepository;
    private final TopicRepository topicRepository;
    private User user;
    private Test test;
    private Result result;


    @Autowired
    public ResultRepositoryTest(ResultRepository resultRepository, ResultRepositoryCustom resultRepositoryCustom, UserRepository userRepository, TestRepository testRepository, TopicRepository topicRepository) {
        this.resultRepository = resultRepository;
        this.resultRepositoryCustom = resultRepositoryCustom;
        this.userRepository = userRepository;
        this.testRepository = testRepository;
        this.topicRepository = topicRepository;
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("ivan");
        user.setEmail("ivanchik@gmail.com");
        user.setPassword("1234");
        user.setRole(Role.STUDENT);
        userRepository.save(user);

        Topic topic = new Topic();
        topic.setName("My Topic");
        topic.setDescription("This is description");
        topicRepository.save(topic);

        test = new Test();
        test.setTitle("My Test");
        test.setDescription("This is description");
        test.setPassingScore(50);
        test.setUser(user);
        test.setTopic(topic);
        testRepository.save(test);

        result = new Result();
        result.setUser(user);
        result.setTest(test);
        result.setScore(80);
        result.setPassed(true);
        resultRepository.save(result);
    }

    @org.junit.jupiter.api.Test
    void repositoryPositive() {
        List<Result> results = resultRepository.findByTestAndUser(test, user);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getScore()).isEqualTo(80);
    }

    @org.junit.jupiter.api.Test
    void repositoryNegative() {
        List<Result> results = resultRepository.findByTestAndUser(test, createAnotherUser());
        assertThat(results).isEmpty();
    }

    @org.junit.jupiter.api.Test
    void customRepositoryPositive() {
        List<Result> results = resultRepositoryCustom.findByTestAndUser(test, user);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getScore()).isEqualTo(80);
    }

    @org.junit.jupiter.api.Test
    void customRepositoryNegative() {
        List<Result> results = resultRepositoryCustom.findByTestAndUser(test, createAnotherUser());
        assertThat(results).isEmpty();
    }

    private User createAnotherUser(){
        User anotherUser = new User();
        anotherUser.setUsername("mike");
        anotherUser.setEmail("mike@example.com");
        anotherUser.setPassword("5678");
        anotherUser.setRole(Role.STUDENT);
        userRepository.save(anotherUser);
        return anotherUser;
    }
}
