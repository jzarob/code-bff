package com.e451.rest.gateways;

import com.e451.rest.domains.assessment.QuestionAnswer;
import com.e451.rest.domains.assessment.QuestionAnswerResponse;
import org.springframework.http.ResponseEntity;

/**
 * Created by j747951 on 6/29/2017.
 */
public interface QuestionAnswerServiceGateway {
    ResponseEntity<QuestionAnswerResponse> createQuestionAnswerResponse(QuestionAnswer questionAnswer,
                                                                        String assessmentGuid);
    ResponseEntity<QuestionAnswerResponse> updateQuestionAnswerResponse(QuestionAnswer questionAnswer,
                                                                        String assessmentGuid);
}
