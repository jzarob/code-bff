package com.e451.rest.services;

import com.e451.rest.domains.assessment.QuestionAnswer;
import com.e451.rest.domains.assessment.QuestionAnswerResponse;
import org.springframework.http.ResponseEntity;

/**
 * Created by j747951 on 6/29/2017.
 */
public interface QuestionAnswerService {
    ResponseEntity<QuestionAnswerResponse> createQuestionAnswer(QuestionAnswer questionAnswer, String assessmentGuid);
    ResponseEntity<QuestionAnswerResponse> updateQuestionAnswer(QuestionAnswer questionAnswer, String assessmentGuid);
}
