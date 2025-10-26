package ru.khamitova.NauJavaProject.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.khamitova.NauJavaProject.entity.Question;
import ru.khamitova.NauJavaProject.repository.CrudRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

//@Repository
//public class QuestionRepository implements CrudRepository<Question, Long> {
//    private final List<Question> questionContainer;
//    private AtomicLong idGenerator = new AtomicLong(1);
//
//    public QuestionRepository(List<Question> questionContainer){
//        this.questionContainer = questionContainer;
//    }
//
//    @Override
//    public void create(Question entity) {
//        questionNotNull(entity);
//
//        if (entity.getId() != null && read(entity.getId()).isPresent()) {
//            throw new IllegalArgumentException("Question with id " + entity.getId() + " already exists");
//        }
//
//        if (entity.getId() == null) {
//            entity.setId(this.idGenerator.getAndIncrement());
//        } else {
//            if (entity.getId() >= this.idGenerator.get()) {
//                idGenerator.set(entity.getId() + 1);
//            }
//        }
//
//        questionContainer.add(entity);
//    }
//
//    @Override
//    public Optional<Question> read(Long id) {
//        idNotNull(id);
//        return questionContainer.stream()
//                .filter(q -> id.equals(q.getId()))
//                .findFirst()
//                .map(this::cloneQuestion);
//        //чтобы последующие изменения не меняли бд делаю clone
//    }
//
//    @Override
//    public List<Question> getAll() {
//        List<Question> copy = new ArrayList<>();
//        for (Question q : questionContainer) {
//            copy.add(cloneQuestion(q));
//        }
//        return copy;
//    }
//
//    @Override
//    public void update(Question entity) {
//        questionNotNull(entity);
//        idNotNull(entity.getId());
//
//        if (read(entity.getId()).isEmpty()){
//            throw new IllegalArgumentException("Question with id " + entity.getId() + " not found");
//        }
//
//        delete(entity.getId());
//        questionContainer.add(entity);
//    }
//
//    @Override
//    public void delete(Long id) {
//        idNotNull(id);
//
//        questionContainer.removeIf(q -> id.equals(q.getId()));
//    }
//
//    private void idNotNull(Long id){
//        if (id == null) {
//            throw new IllegalArgumentException("Question id can't be null");
//        }
//    }
//
//    private void questionNotNull(Question entity){
//        if (entity == null) {
//            throw new IllegalArgumentException("Question can't be null");
//        }
//    }
//
//    private Question cloneQuestion(Question original) {
//        Question clone = new Question();
//        clone.setId(original.getId());
//        clone.setText(original.getText());
//        clone.setPoints(original.getPoints());
//        clone.setType(original.getType());
//        clone.setAnswers(new HashMap<>(original.getAnswers()));
//        return clone;
//    }
//}
