package com.e451.rest.gateways;

import com.e451.rest.domains.question.QuestionResponse;
import org.springframework.http.ResponseEntity;


/**
 * Created by e384873 on 6/9/2017.
 */
public interface QuestionServiceGateway {
    ResponseEntity<QuestionResponse> getQuestions();
}
