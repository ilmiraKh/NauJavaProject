package ru.khamitova.NauJavaProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.khamitova.NauJavaProject.entity.User;
import ru.khamitova.NauJavaProject.service.UserService;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute("user") User user, Model model){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.addUser(user);
            return "redirect:/login";
        } catch (RuntimeException ex) {
            model.addAttribute("message", ex.getMessage());
            return "registration";
        }
    }
}
