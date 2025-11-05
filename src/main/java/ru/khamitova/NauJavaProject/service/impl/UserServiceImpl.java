package ru.khamitova.NauJavaProject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.khamitova.NauJavaProject.entity.User;
import ru.khamitova.NauJavaProject.repository.UserRepository;
import ru.khamitova.NauJavaProject.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUsername(String username){
        return userRepository.findUserByEmail(username)
                .orElseThrow(() -> new RuntimeException("User with email " + username + " not found"));
        // в качестве username выступает email - он уникальный
        // использовавшийся до этого username переименовала в login чтобы избежать путаницы
    }

    public void addUser(User user){
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        userRepository.save(user);
    }
}
