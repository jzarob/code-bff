package com.e451.rest.gateways;

import com.e451.rest.domains.question.QuestionResponse;
import com.e451.rest.gateways.impl.QuestionServiceGatewayImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


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

}
