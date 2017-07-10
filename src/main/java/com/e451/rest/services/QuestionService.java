package com.e451.rest.services;

import com.e451.rest.domains.language.LanguageResponse;
import com.e451.rest.domains.question.Question;
import com.e451.rest.domains.question.QuestionResponse;
import org.springframework.http.ResponseEntity;

/**
 * Created by e384873 on 6/9/2017.
 */
public interface QuestionService {
    ResponseEntity<QuestionResponse> getQuestions();
    ResponseEntity<QuestionResponse> getQuestion(String id);
    ResponseEntity<QuestionResponse> createQuestion(Question question);
    ResponseEntity<QuestionResponse> updateQuestion(Question question);
    ResponseEntity deleteQuestion(String id);
    ResponseEntity<LanguageResponse> getLanguages();
}
