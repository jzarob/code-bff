package com.e451.rest.gateways;

import com.e451.rest.domains.assessment.QuestionAnswer;
import com.e451.rest.domains.assessment.QuestionAnswerResponse;
import com.e451.rest.domains.question.Question;
import com.e451.rest.gateways.impl.QuestionAnswerServiceServiceGatewayImpl;
import com.e451.rest.services.QuestionAnswerServiceTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by j747951 on 6/29/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuestionAnswerServiceGatewayImplTest {

    @Mock
    private RestTemplate restTemplate;

    private QuestionAnswerServiceServiceGatewayImpl questionAnswerServiceGateway;

    private static final String BASE_URI = "fakeUri/assessments/guid1";

    @Before
    public void setup() {
        questionAnswerServiceGateway = new QuestionAnswerServiceServiceGatewayImpl("fakeUri", restTemplate);
    }

    @Test
    public void whenCreateQuestionAnswerCalled_thenRestTemplateIsCalled() throws Exception {
        QuestionAnswerResponse questionAnswerResponse = new QuestionAnswerResponse();
        final QuestionAnswer questionAnswer = new QuestionAnswer("t1", "b1", "a1", "1");
        questionAnswerResponse.setQuestionAnswers(Arrays.asList(questionAnswer));
        ResponseEntity<QuestionAnswerResponse> response = ResponseEntity.ok(questionAnswerResponse);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(BASE_URI).pathSegment("answers");

        when(restTemplate.postForEntity(uriComponentsBuilder.build().toString(), questionAnswer, QuestionAnswerResponse.class)).thenReturn(response);

        questionAnswerServiceGateway.createQuestionAnswerResponse(questionAnswer, "guid1");

        verify(restTemplate).postForEntity(uriComponentsBuilder.build().toString(), questionAnswer, QuestionAnswerResponse.class);

    }

    @Test
    public void whenUpdateQuestionAnswerCalled_thenRestTemplateIsCalled() throws Exception {
        QuestionAnswerResponse questionAnswerResponse = new QuestionAnswerResponse();
        final QuestionAnswer questionAnswer = new QuestionAnswer("t1", "b1", "a1", "1");
        questionAnswerResponse.setQuestionAnswers(Arrays.asList(questionAnswer));


        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(BASE_URI).pathSegment("answers");
        HttpEntity<QuestionAnswer> questionAnswerHttpEntity = new HttpEntity<>(questionAnswer, null);
        ResponseEntity<QuestionAnswerResponse> response = ResponseEntity.ok(questionAnswerResponse);

        when(restTemplate.exchange(uriComponentsBuilder.build().toString(), HttpMethod.PUT, questionAnswerHttpEntity, QuestionAnswerResponse.class)).thenReturn(response);

        questionAnswerServiceGateway.updateQuestionAnswerResponse(questionAnswer, "guid1");

        verify(restTemplate).exchange(uriComponentsBuilder.build().toString(), HttpMethod.PUT,
                questionAnswerHttpEntity, QuestionAnswerResponse.class);

    }
}
