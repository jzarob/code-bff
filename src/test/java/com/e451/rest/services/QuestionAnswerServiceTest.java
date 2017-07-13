package com.e451.rest.services;

import com.e451.rest.domains.assessment.QuestionAnswer;
import com.e451.rest.domains.assessment.QuestionAnswerResponse;
import com.e451.rest.gateways.QuestionAnswerServiceGateway;
import com.e451.rest.services.impl.QuestionAnswerServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by j747951 on 6/29/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuestionAnswerServiceTest {

    private QuestionAnswerService questionAnswerService;

    @Mock
    private QuestionAnswerServiceGateway questionAnswerServiceGateway;

    private List<QuestionAnswer> questionAnswers;

    @Before
    public void setup() {
        this.questionAnswerService = new QuestionAnswerServiceImpl(questionAnswerServiceGateway);

        this.questionAnswers = Arrays.asList(
                new QuestionAnswer("q1", "b1", "id1", "id1"),
                new QuestionAnswer("q2", "b2", "id2", "id2"),
                new QuestionAnswer("q3", "b3", "id3", "id3")
        );
    }

    @Test
    public void whenCreateQuestionAnswer_ReturnNewQuestionAnswer() {
        QuestionAnswerResponse response = new QuestionAnswerResponse();
        QuestionAnswer answer = new QuestionAnswer("t1", "b1", "a1", "1");
        response.setQuestionAnswers(Arrays.asList(answer));

        ResponseEntity<QuestionAnswerResponse> gatewayResponse = ResponseEntity.status(HttpStatus.CREATED).body(response);

        when(questionAnswerServiceGateway.createQuestionAnswerResponse(answer, "guid1")).thenReturn(gatewayResponse);

        ResponseEntity<QuestionAnswerResponse> responseEntity = questionAnswerService.createQuestionAnswer(answer, "guid1");

        Assert.assertEquals(1, responseEntity.getBody().getQuestionAnswers().size());
    }

    @Test
    public void whenUpdateQuestionAnswer_ReturnUpdatedQuestionAnswer() {
        QuestionAnswerResponse response = new QuestionAnswerResponse();
        QuestionAnswer answer = new QuestionAnswer("t1", "b1", "a1", "1");
        response.setQuestionAnswers(Arrays.asList(answer));

        ResponseEntity<QuestionAnswerResponse> gatewayResponse = new ResponseEntity<QuestionAnswerResponse>(response, HttpStatus.ACCEPTED);

        when(questionAnswerServiceGateway.updateQuestionAnswerResponse(answer, "guid1")).thenReturn(gatewayResponse);

        ResponseEntity<QuestionAnswerResponse> responseEntity = questionAnswerService.updateQuestionAnswer(answer, "guid1");

        Assert.assertEquals(1, responseEntity.getBody().getQuestionAnswers().size());
    }
}
