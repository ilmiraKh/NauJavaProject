package ru.khamitova.NauJavaProject.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.khamitova.NauJavaProject.entity.User;
import ru.khamitova.NauJavaProject.entity.enums.Role;
import ru.khamitova.NauJavaProject.repository.UserRepository;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// чтобы не запускать вручную приложение перед запуском теста
public class LoginLogoutTest {
    private static WebDriver driver;
    @LocalServerPort
    private int port;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private User user;
    private final static String USERNAME = "test@gmail.com";
    private final static String PASSWORD = "password1!!**";

    @Autowired
    public LoginLogoutTest(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeAll
    static void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeEach
    public void setupUser(){
        user = new User();
        user.setEmail("test@gmail.com");
        user.setLogin("test");
        user.setPassword(PASSWORD);
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setRole(Role.TEACHER);
        user = userRepository.save(user);
    }

    @AfterEach
    public void cleanup(){
        if (user != null) userRepository.deleteById(user.getId());
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void loginLogoutTest() throws InterruptedException {
        String baseUrl = "http://localhost:" + port;
        driver.get(baseUrl + "/login");

        WebElement usernameInput = driver.findElement(By.name("username"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        usernameInput.sendKeys(USERNAME);
        passwordInput.sendKeys(PASSWORD);
        loginButton.click();

        Thread.sleep(1000); // для загрузки страницы

        String currentUrl = driver.getCurrentUrl();
        // добавила контроллер для "домашней" страницы
        assertTrue(currentUrl.contains("/home"), "User should be redirected to home page after login");

        WebElement logoutButton = driver.findElement(By.id("logoutBtn"));
        logoutButton.click();

        Thread.sleep(1000);

        currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/login"),
                "User should be redirected to login page after logout");
    }

}
