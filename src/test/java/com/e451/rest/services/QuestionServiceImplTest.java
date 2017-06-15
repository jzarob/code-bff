package com.e451.rest.services;

import com.e451.rest.domains.question.Question;
import com.e451.rest.domains.question.QuestionResponse;
import com.e451.rest.gateways.QuestionServiceGateway;
import com.e451.rest.services.impl.QuestionServiceImpl;
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
 * Created by l659598 on 6/12/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceImplTest {

    private QuestionService questionService;

    @Mock
    private QuestionServiceGateway questionServiceGateway;

    private List<Question> questions;

    @Before
    public void setup() {
        this.questionService = new QuestionServiceImpl(questionServiceGateway);
        questions = Arrays.asList(
                new Question("1", "q1", "a1", "t1", 5),
                new Question("2", "q2", "a2", "t1", 4)
                );
    }

    @Test
    public void whenGetQuestions_returnsListOfQuestions() {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setQuestions(this.questions);

        ResponseEntity<QuestionResponse> gatewayResponse = new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.OK);

        when(questionServiceGateway.getQuestions()).thenReturn(gatewayResponse);

        ResponseEntity<QuestionResponse> response = questionService.getQuestions();

        Assert.assertTrue(response.getBody().getQuestions().size() == 2);
    }

    @Test
    public void whenGetQuestion_returnListOfSizeOne() {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setQuestions(Arrays.asList(this.questions.get(0)));

        ResponseEntity<QuestionResponse> gatewayResponse = new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.OK);

        when(questionServiceGateway.getQuestion("1")).thenReturn(gatewayResponse);

        ResponseEntity<QuestionResponse> response = questionService.getQuestion("1");

        Assert.assertTrue(response.getBody().getQuestions().size() == 1);
    }

    @Test
    public void whenCreateQuestion_returnNewQuestion() {
        QuestionResponse questionResponse = new QuestionResponse();
        Question question = new Question("3", "q3", "a3", "t4", 3);
        questionResponse.setQuestions(Arrays.asList(question));

        ResponseEntity<QuestionResponse> gatewayResponse = new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.CREATED);

        when(questionServiceGateway.createQuestion(question)).thenReturn(gatewayResponse);

        ResponseEntity<QuestionResponse> response = questionService.createQuestion(question);

        Assert.assertEquals(1, response.getBody().getQuestions().size());
        Assert.assertEquals(question, response.getBody().getQuestions().get(0));
    }

    @Test
    public void whenUpdateQuestion_returnUpdatedQuestion() {
        QuestionResponse questionResponse = new QuestionResponse();

        Question question = new Question("2", "q3", "a3", "t3", 2);
        questionResponse.setQuestions(Arrays.asList(question));

        ResponseEntity<QuestionResponse> gatewayResponse = new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.ACCEPTED);

        when(questionServiceGateway.updateQuestion(question)).thenReturn(gatewayResponse);

        ResponseEntity<QuestionResponse> response = questionService.updateQuestion(question);

        Assert.assertEquals(1, response.getBody().getQuestions().size());
        Assert.assertEquals(question, response.getBody().getQuestions().get(0));
    }

    @Test
    public void whenDeleteQuestion_returnResponseEntity() {
        ResponseEntity gatewayResponse = new ResponseEntity(null, HttpStatus.NO_CONTENT);

        when(questionServiceGateway.deleteQuestion("1")).thenReturn(gatewayResponse);

        ResponseEntity response = questionService.deleteQuestion("1");

        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
