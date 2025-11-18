package ru.khamitova.NauJavaProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeControllerView{

    @GetMapping
    public String homeView(){
        return "home";
    }
}
