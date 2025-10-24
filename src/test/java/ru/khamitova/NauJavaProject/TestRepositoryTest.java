package ru.khamitova.NauJavaProject;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.khamitova.NauJavaProject.entity.Test;
import ru.khamitova.NauJavaProject.entity.Topic;
import ru.khamitova.NauJavaProject.entity.User;
import ru.khamitova.NauJavaProject.entity.enums.Role;
import ru.khamitova.NauJavaProject.repository.TestRepository;
import ru.khamitova.NauJavaProject.repository.TopicRepository;
import ru.khamitova.NauJavaProject.repository.UserRepository;
import ru.khamitova.NauJavaProject.repository.custom.TestRepositoryCustom;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class TestRepositoryTest {
    // тестирую методы для обоих репозиториев Test - из п.5 (Query) и п.6 (Criteria API)
    private final TestRepository testRepository;
    private final TestRepositoryCustom testRepositoryCustom;

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public TestRepositoryTest(TestRepository testRepository, TestRepositoryCustom testRepositoryCustom, UserRepository userRepository, TopicRepository topicRepository) {
        this.testRepository = testRepository;
        this.testRepositoryCustom = testRepositoryCustom;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("teacher");
        user.setEmail("olga1967@gmail.com");
        user.setPassword("1234");
        user.setRole(Role.TEACHER);
        userRepository.save(user);

        Topic topic = new Topic();
        topic.setName("My topic");
        topic.setDescription("This is description");
        topicRepository.save(topic);

        testRepository.save(createTest(topic, user, "1"));

        Topic anotherTopic = new Topic();
        anotherTopic.setName("My second topic");
        anotherTopic.setDescription("description");
        topicRepository.save(anotherTopic);

        testRepository.save(createTest(anotherTopic, user, "2"));
    }

    @org.junit.jupiter.api.Test
    void repositoryPositive() {
        List<Test> tests = testRepository.findAllByTopicName("My topic");
        assertThat(tests).hasSize(1);
        assertThat(tests.get(0).getTitle()).isEqualTo("1");
    }

    @org.junit.jupiter.api.Test
    void repositoryNegative() {
        List<Test> tests = testRepository.findAllByTopicName("NonExistingTopic");
        assertThat(tests).isEmpty();
    }

    @org.junit.jupiter.api.Test
    void repositoryCustomPositive() {
        List<Test> tests = testRepositoryCustom.findAllByTopicName("My topic");
        assertThat(tests).hasSize(1);
        assertThat(tests.get(0).getTitle()).isEqualTo("1");
    }

    @org.junit.jupiter.api.Test
    void repositoryCustomNegative() {
        List<Test> tests = testRepositoryCustom.findAllByTopicName("NonExistingTopic");
        assertThat(tests).isEmpty();
    }

    private Test createTest(Topic topic, User user, String name){
        Test test = new Test();
        test.setTitle(name);
        test.setDescription("This is description");
        test.setPassingScore(50);
        test.setTopic(topic);
        test.setUser(user);
        return test;
    }
}
