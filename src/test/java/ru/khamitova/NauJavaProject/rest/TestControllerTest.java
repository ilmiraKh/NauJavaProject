package ru.khamitova.NauJavaProject.rest;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.khamitova.NauJavaProject.entity.Topic;
import ru.khamitova.NauJavaProject.entity.User;
import ru.khamitova.NauJavaProject.entity.enums.Role;
import ru.khamitova.NauJavaProject.repository.TestRepository;
import ru.khamitova.NauJavaProject.repository.TopicRepository;
import ru.khamitova.NauJavaProject.repository.UserRepository;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestControllerTest {
    @LocalServerPort
    private int port;
    private SessionFilter sessionFilter;
    private final TopicRepository topicRepository;
    private final TestRepository testRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private User user;
    private ru.khamitova.NauJavaProject.entity.Test test;
    private Topic topic;
    private final static String USERNAME = "test@gmail.com";
    private final static String PASSWORD = "password1!!**";

    @Autowired
    public TestControllerTest(TopicRepository topicRepository, TestRepository testRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.topicRepository = topicRepository;
        this.testRepository = testRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";

        prepareDatabase();

        // т.к. все рест-эндпойнты недоступны неавторизованным пользователям
        sessionFilter = login();

    }

    @AfterEach
    void cleanup() {
        if (test != null) testRepository.deleteById(test.getId());
        if (topic != null) topicRepository.deleteById(topic.getId());
        if (user != null) userRepository.deleteById(user.getId());
    }

    @Test
    public void findByTopicNameSuccess(){
        given()
                .filter(sessionFilter)
                .queryParam("topicName", topic.getName())
                .when()
                .get("/custom/tests/findByTopicName")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", not(empty()))
                .body("[0].id", notNullValue())
                .body("[0].topic.name", equalTo(topic.getName()));
    }

    @Test
    void testFindByTopicNameNoResults() {
        given()
                .filter(sessionFilter)
                .queryParam("topicName", "unknown topic")
                .when()
                .get("/custom/tests/findByTopicName")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", empty());
    }

    @Test
    void testMissingTopicName() {
        given()
                .filter(sessionFilter)
                .when()
                .get("/custom/tests/findByTopicName")
                .then()
                .statusCode(500);
    }

    private void prepareDatabase(){
        topic = new Topic();
        topic.setName("topic");
        topic = topicRepository.save(topic);

        user = new User();
        user.setEmail(USERNAME);
        user.setLogin("test");
        user.setPassword(PASSWORD);
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setRole(Role.TEACHER);
        user = userRepository.save(user);

        test = new ru.khamitova.NauJavaProject.entity.Test();
        test.setTopic(topic);
        test.setTitle("test!!");
        test.setPassingScore(10);
        test.setUser(user);
        test = testRepository.save(test);
    }

    private SessionFilter login(){
        SessionFilter filter = new SessionFilter();
        String loginPageHtml = given()
                .filter(filter)
                .when()
                .get("/login")
                .then()
                .statusCode(200)
                .extract()
                .asString();

        Document doc = Jsoup.parse(loginPageHtml);
        Element csrfInput = doc.selectFirst("input[name=_csrf]");
        String csrfToken = csrfInput.attr("value");

        given()
                .filter(filter)
                .formParam("username", USERNAME)
                .formParam("password", PASSWORD)
                .formParam("_csrf", csrfToken)
                .when()
                .post("/login")
                .then()
                .statusCode(302);
        return filter;
    }
}
