package ru.khamitova.NauJavaProject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.khamitova.NauJavaProject.entity.Answer;
import ru.khamitova.NauJavaProject.entity.Question;
import ru.khamitova.NauJavaProject.repository.AnswerRepository;
import ru.khamitova.NauJavaProject.repository.QuestionRepository;
import ru.khamitova.NauJavaProject.service.QuestionService;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;
    private PlatformTransactionManager transactionManager;

    @Autowired
    public QuestionServiceImpl(AnswerRepository answerRepository, QuestionRepository questionRepository, PlatformTransactionManager transactionManager) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void deleteQuestion(Question q) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try{
            List<Answer> answers = answerRepository.findByQuestion(q);
            answerRepository.deleteAll(answers);

            questionRepository.delete(q);

            transactionManager.commit(status);
        }catch (DataAccessException ex){
            transactionManager.rollback(status);
            throw ex;
        }
    }
}
