package ru.khamitova.NauJavaProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.khamitova.NauJavaProject.entity.User;
import ru.khamitova.NauJavaProject.repository.UserRepository;

@Controller
@RequestMapping("/custom/users/view")
public class UserControllerView {
    private final UserRepository userRepository;

    @Autowired
    public UserControllerView(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/list")public String userListView(Model model){
        Iterable<User> products = userRepository.findAll();
        model.addAttribute("users", products);
        return "userList";
    }
}
