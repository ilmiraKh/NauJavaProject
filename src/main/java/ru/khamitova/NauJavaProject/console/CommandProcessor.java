package ru.khamitova.NauJavaProject.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.khamitova.NauJavaProject.entity.Question;
import ru.khamitova.NauJavaProject.entity.enums.QuestionType;
import ru.khamitova.NauJavaProject.service.QuestionService;

import java.sql.SQLOutput;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CommandProcessor {
    private final QuestionService questionService;

    public CommandProcessor(QuestionService questionService) {
        this.questionService = questionService;
    }

    public void processCommand(String input) {
        List<String> cmd = mySplit(input);
        String command = cmd.get(0).toLowerCase();

        try {
            switch (command) {
                case "create" -> create(cmd);
                case "edit" -> edit(cmd);
                case "show" -> show(cmd);
                case "answer" -> answer(cmd);
                case "delete" -> delete(cmd);
                default -> System.out.println("Unknown command");
            }
        }  catch (NumberFormatException e) {
            System.out.println("Incorrect syntax");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private List<String> mySplit(String input) {
        List<String> parts = new ArrayList<>();
        Matcher m = Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(input);
        while (m.find()) {
            if (m.group(1) != null) {
                parts.add(m.group(1));
            } else {
               parts.add(m.group(2));
            }
        }
        return parts;
    }

    // create c|o "TEXT" POINTS "ANSWER1;ANSWER2;..." [CORRECT_ANSWER_INDEX]
    private void create(List<String> args){
        if (args.size() < 5) {
            System.out.println("Too few arguments");
            return;
        }

        String typeFlag = args.get(1).trim().toLowerCase();
        String text = args.get(2);
        int points = Integer.parseInt(args.get(3));
        String answersRaw = args.get(4);
        Map<String, Boolean> answers = new HashMap<>();

        if (typeFlag.equals("c")) {
            if (args.size() < 6) {
                System.out.println("Too few arguments");
                return;
            }

            int correctIndex = Integer.parseInt(args.get(5));

            String[] options = answersRaw.split(";");
            if (correctIndex < 1 || correctIndex > options.length) {
                System.out.println("Wrong index of correct answer");
                return;
            }

            for (int i = 0; i < options.length; i++) {
                boolean isCorrect = (i + 1) == correctIndex;
                answers.put(options[i], isCorrect);
            }

            questionService.createQuestion(text, points, QuestionType.CHOICE, answers);
            System.out.println("Question successfully created!");

        } else if (typeFlag.equals("o")) {
            String[] correctAnswers = answersRaw.split(";");
            for (String ans : correctAnswers) {
                answers.put(ans, true);
            }

            questionService.createQuestion(text, points, QuestionType.OPEN, answers);
            System.out.println("Question successfully created!");

        } else {
            System.out.println("Incorrect type of question");
        }
    }

    //edit ID "NEW_TEXT"
    private void edit(List<String> args){
        if (args.size() < 3) {
            System.out.println("Too few arguments");
            return;
        }

        long id = Long.parseLong(args.get(1));
        String newText = args.get(2);

        questionService.editQuestionText(id, newText);
        System.out.println("Question text successfully updated!");

    }

    //show [ID]
    private void show(List<String> args){
        if (args.size() == 2) {
            long id = Long.parseLong(args.get(1));
            Optional<Question> questionOpt = questionService.getQuestionById(id);
            if (questionOpt.isEmpty()) {
                System.out.println("Question with id " + id + " not found");
                return;
            }

            Question q = questionOpt.get();
            System.out.println("Id: " + q.getId());
            System.out.println("Text: " + q.getText());
            System.out.println("Points: " + q.getPoints());
            System.out.println("Type: " + q.getType());

            if (q.getType() == QuestionType.CHOICE) {
                System.out.println("Answers:");
                int index = 1;
                for (Map.Entry<String, Boolean> entry : q.getAnswers().entrySet()) {
                    System.out.println("    " + index + ") " + entry.getKey());
                    index += 1;
                }
            }
            // предполагается, что вопрос выводится для того чтобы на него ответили
            // поэтому не показываю, какие ответы правильные

        } else if (args.size() == 1) {
            // краткий вывод всех вопросов
            List<Question> questions = questionService.getAll();
            if (questions.isEmpty()) {
                System.out.println("No questions created.");
                return;
            }

            for (Question q : questions) {
                System.out.println("Id: " + q.getId() + ". " + q.getText());
            }

        } else {
            System.out.println("Too many values to unpack");
        }
    }

    //answer ID "YOUR_ANSWER"
    private void answer(List<String> args){
        if (args.size() < 3) {
            System.out.println("Too few arguments");
            return;
        }

        long id = Long.parseLong(args.get(1));
        String userAnswer = args.get(2);

        int points = questionService.answerQuestion(id, userAnswer);
        if (points > 0) {
            System.out.println("Correct! You earned " + points + " points. Your total score is " + questionService.getTotalScore());
        } else {
            System.out.println("Wrong answer.");
        }
    }

    private void delete(List<String> args){
        if (args.size() < 2) {
            System.out.println("Too few arguments");
            return;
        }
        if (args.size() > 2){
            System.out.println("Too many values to unpack");
            return;
        }

        long id = Long.parseLong(args.get(1));
        if (questionService.deleteQuestion(id)) {
            System.out.println("Question successfully deleted.");
        } else {
            System.out.println("Question with id " + id + " not found");
        }
    }
}
