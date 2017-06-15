package com.e451.rest.gateways;

import com.e451.rest.domains.question.Question;
import com.e451.rest.domains.question.QuestionResponse;
import com.e451.rest.gateways.impl.QuestionServiceGatewayImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by l659598 on 6/9/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceGatewayImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    private QuestionServiceGateway questionServiceGateway;

    @Before
    public void setup() {
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        questionServiceGateway = new QuestionServiceGatewayImpl("fakeUri", restTemplateBuilder);
    }

    @Test
    public void whenGetQuestionsCalled_thenRestTemplateIsCalled() throws Exception {
        QuestionResponse questionResponse = new QuestionResponse();
        ResponseEntity<QuestionResponse> response = ResponseEntity.ok(questionResponse);

        when(restTemplate.getForEntity("fakeUri/questions", QuestionResponse.class)).thenReturn(response);

        questionServiceGateway.getQuestions();

        verify(restTemplate).getForEntity("fakeUri/questions", QuestionResponse.class);
    }

    @Test
    public void whenGetQuestionCalled_thenRestTemplateIsCalled() throws Exception {
        QuestionResponse questionResponse = new QuestionResponse();
        ResponseEntity<QuestionResponse> response = ResponseEntity.ok(questionResponse);

        when(restTemplate.getForEntity("fakeUri/questions/1", QuestionResponse.class)).thenReturn(response);

        questionServiceGateway.getQuestion("1");

        verify(restTemplate).getForEntity("fakeUri/questions/1", QuestionResponse.class);
    }

    @Test
    public void whenCreateQuestionCalled_thenRestTemplateIsCalled() throws Exception {
        QuestionResponse questionResponse = new QuestionResponse();
        ResponseEntity<QuestionResponse> response = ResponseEntity.ok(questionResponse);

        Question question = new Question("1", "hello?", "hey", "greeting", 2);

        when(restTemplate.postForEntity("fakeUri/questions", question, QuestionResponse.class)).thenReturn(response);

        questionServiceGateway.createQuestion(question);

        verify(restTemplate).postForEntity("fakeUri/questions", question, QuestionResponse.class);
    }

    @Test
    public void whenUpdateQuestionCalled_thenRestTemplateIsCalled() throws Exception {
        QuestionResponse questionResponse = new QuestionResponse();

        Question question = new Question("1", "hello?", "hey", "greeting", 2);

        questionResponse.setQuestions(Arrays.asList(question));

        HttpEntity<Question> questionHttpEntity = new HttpEntity<>(question, null);
        ResponseEntity<QuestionResponse> responseEntity = ResponseEntity.ok(questionResponse);


        when(restTemplate.exchange(new URI("fakeUri/questions"), HttpMethod.PUT, questionHttpEntity, QuestionResponse.class)).thenReturn(responseEntity);

        questionServiceGateway.updateQuestion(question);

        verify(restTemplate).exchange(new URI("fakeUri/questions"), HttpMethod.PUT, questionHttpEntity, QuestionResponse.class);
    }

    @Test
    public void whenDeleteQuestionCalled_thenRestTemplateIsCalled() throws Exception {

        URI uri = new URI("fakeUri/questions/1");

        HttpEntity request = new HttpEntity(null, null);

        when(restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build());

        questionServiceGateway.deleteQuestion("1");

        verify(restTemplate).exchange(uri, HttpMethod.DELETE, request, Object.class);
    }

}
