package com.e451.rest.services.impl;

import com.e451.rest.domains.language.LanguageResponse;
import com.e451.rest.domains.question.Question;
import com.e451.rest.domains.question.QuestionResponse;
import com.e451.rest.gateways.QuestionServiceGateway;
import com.e451.rest.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by e384873 on 6/9/2017.
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionServiceGateway questionServiceGateway;

    @Autowired
    public QuestionServiceImpl(QuestionServiceGateway questionServiceGateway) {
        this.questionServiceGateway = questionServiceGateway;
    }

    @Override
    public ResponseEntity<QuestionResponse> getQuestions() {
        return questionServiceGateway.getQuestions();
    }

    @Override
    public ResponseEntity<QuestionResponse> getQuestions(int page, int size, String property) {
        return questionServiceGateway.getQuestions(page, size, property);
    }

    @Override
    public ResponseEntity<LanguageResponse> getLanguages() { return questionServiceGateway.getLanguages(); }

    @Override
    public ResponseEntity<QuestionResponse> getQuestion(String id) {
        return questionServiceGateway.getQuestion(id);
    }

    @Override
    public ResponseEntity<QuestionResponse> createQuestion(Question question) {
        return questionServiceGateway.createQuestion(question);
    }

    @Override
    public ResponseEntity<QuestionResponse> updateQuestion(Question question) {
        return questionServiceGateway.updateQuestion(question);
    }

    @Override
    public ResponseEntity deleteQuestion(String id) {
        return questionServiceGateway.deleteQuestion(id);
    }
}
