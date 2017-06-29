package com.e451.rest.services.impl;

import com.e451.rest.domains.assessment.QuestionAnswer;
import com.e451.rest.domains.assessment.QuestionAnswerResponse;
import com.e451.rest.gateways.QuestionAnswerServiceGateway;
import com.e451.rest.services.QuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by j747951 on 6/29/2017.
 */
@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService {

    private QuestionAnswerServiceGateway questionAnswerServiceGateway;

    @Autowired
    public QuestionAnswerServiceImpl(QuestionAnswerServiceGateway questionAnswerServiceGateway) {
        this.questionAnswerServiceGateway = questionAnswerServiceGateway;
    }

    @Override
    public ResponseEntity<QuestionAnswerResponse> createQuestionAnswer(QuestionAnswer questionAnswer,
                                                                       String assessmentGuid) {
        return this.questionAnswerServiceGateway.createQuestionAnswerResponse(questionAnswer, assessmentGuid);
    }

    @Override
    public ResponseEntity<QuestionAnswerResponse> updateQuestionAnswer(QuestionAnswer questionAnswer,
                                                                       String assessmentGuid) {
        return this.questionAnswerServiceGateway.updateQuestionAnswerResponse(questionAnswer, assessmentGuid);
    }
}
