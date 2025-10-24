package ru.khamitova.NauJavaProject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.khamitova.NauJavaProject.console.CommandProcessor;

import java.util.Scanner;

@Configuration
public class ConsoleConfig {
    private CommandProcessor commandProcessor;

    public ConsoleConfig(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    @Bean
    public CommandLineRunner commandScanner() {
        return args -> {
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("""
                        Input command. Available commands and their syntax:
                        create c|o "TEXT" POINTS "ANSWER1;ANSWER2;..." [CORRECT_ANSWER_INDEX] - create question
                            c - choice type (input all answer options, then specify correct answer index)
                            o - open type (input all correct answers).
                        show [ID] - show all questions or specific question by id.
                        edit ID "NEW_TEXT" - edit the text of question by id.
                        delete ID - delete question by id
                        answer ID "YOUR_ANSWER" - answer question by id.
                        exit - for exit.""");
                while (true) {
                    System.out.print("> ");
                    String input = scanner.nextLine();
                    if ("exit".equalsIgnoreCase(input.trim())) {
                        System.out.println("Bye!");
                        break;
                    }
                    if (!input.trim().isEmpty()) {
                        commandProcessor.processCommand(input);
                    }
                }
            }
        };
    }
}