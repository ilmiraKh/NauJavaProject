package ru.khamitova.NauJavaProject.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.khamitova.NauJavaProject.entity.User;
import ru.khamitova.NauJavaProject.repository.UserRepository;
import ru.khamitova.NauJavaProject.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@gmail.com");
        user.setLogin("testUser");
    }

    @Test
    public void findUserByUsernameSuccess(){
        when(userRepository.findUserByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        User result = userService.findUserByUsername("test@gmail.com");

        assertNotNull(result);
        assertEquals("test@gmail.com", result.getEmail());
        verify(userRepository, times(1)).findUserByEmail("test@gmail.com");
    }

    @Test
    public void findUserByUsernameException(){
        //негативный сценарий теста - бросается исключение
        when(userRepository.findUserByEmail("notfound@gmail.com"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.findUserByUsername("notfound@gmail.com"));

        assertEquals("User with email notfound@gmail.com not found", exception.getMessage());
        verify(userRepository, times(1)).findUserByEmail("notfound@gmail.com");
    }

    @Test
    public void addUserSuccess(){
        when(userRepository.findUserByEmail("test@gmail.com"))
                .thenReturn(Optional.empty());
        userService.addUser(user);
        
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void addUserException(){
        //так же: негативный сценарий теста - бросается исключение
        when(userRepository.findUserByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.addUser(user));

        assertEquals("User with this email already exists", exception.getMessage());
        verify(userRepository, never()).save(any());
    }
}
