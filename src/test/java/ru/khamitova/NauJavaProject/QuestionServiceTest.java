package ru.khamitova.NauJavaProject;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.khamitova.NauJavaProject.entity.*;
import ru.khamitova.NauJavaProject.entity.enums.QuestionType;
import ru.khamitova.NauJavaProject.entity.enums.Role;
import ru.khamitova.NauJavaProject.repository.*;
import ru.khamitova.NauJavaProject.service.QuestionService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class QuestionServiceTest {
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final TestRepository testRepository;
    private Question question;

    @Autowired
    public QuestionServiceTest(QuestionService questionService, QuestionRepository questionRepository, AnswerRepository answerRepository, UserRepository userRepository, TopicRepository topicRepository, TestRepository testRepository) {
        this.questionService = questionService;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.testRepository = testRepository;
    }

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setLogin("teacher");
        user.setEmail("olga1967@gmail.com");
        user.setPassword("1234");
        user.setRole(Role.TEACHER);
        userRepository.save(user);

        Topic topic = new Topic();
        topic.setName("My topic");
        topic.setDescription("This is description");
        topicRepository.save(topic);

        Test test = new Test();
        test.setTitle("My Test");
        test.setDescription("This is description");
        test.setPassingScore(50);
        test.setUser(user);
        test.setTopic(topic);
        testRepository.save(test);

        question = new Question();
        question.setText("What is our life");
        question.setType(QuestionType.SINGLE_CHOICE);
        question.setPoints(5);
        question.setOrderIndex(1);
        question.setTest(test);
        questionRepository.save(question);

        Answer a1 = new Answer();
        a1.setText("Nobody knows");
        a1.setCorrect(true);
        a1.setQuestion(question);
        answerRepository.save(a1);
    }

    @org.junit.jupiter.api.Test
    void positive() {
        assertThat(answerRepository.findByQuestion(question)).hasSize(1);

        questionService.deleteQuestion(question);

        assertThat(questionRepository.findById(question.getId())).isEmpty();
        assertThat(answerRepository.findByQuestion(question)).isEmpty();
    }

    @org.junit.jupiter.api.Test
    void rollback() {
        Question fake = new Question();
        fake.setId(999L);

        questionService.deleteQuestion(fake);

        assertThat(questionRepository.findById(question.getId())).isPresent();
        assertThat(answerRepository.findByQuestion(question)).hasSize(1);
    }
}
