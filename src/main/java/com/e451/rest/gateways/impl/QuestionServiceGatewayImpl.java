package com.e451.rest.gateways.impl;

import com.e451.rest.domains.question.Question;
import com.e451.rest.domains.question.QuestionResponse;
import com.e451.rest.gateways.QuestionServiceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by e384873 on 6/9/2017.
 */
@Service
public class QuestionServiceGatewayImpl implements QuestionServiceGateway {

    private final String questionServiceUri;
    private final RestTemplateBuilder restTemplateBuilder;
    private final RestTemplate restTemplate;

    @Autowired
    public QuestionServiceGatewayImpl(@Value("${service-uri}") String questionServiceUri,
                                      RestTemplateBuilder restTemplateBuilder,
                                      RestTemplate restTemplate) {
        this.questionServiceUri = questionServiceUri + "/questions";
        this.restTemplateBuilder = restTemplateBuilder;
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<QuestionResponse> getQuestions() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(questionServiceUri);
        return restTemplate.getForEntity(builder.build().toUriString(), QuestionResponse.class);
    }

    @Override
    public ResponseEntity<QuestionResponse> getQuestion(String id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(questionServiceUri).pathSegment(id);

        RestTemplate template = restTemplateBuilder.build();

        return template.getForEntity(builder.build().toUriString(), QuestionResponse.class);
    }

    @Override
    public ResponseEntity<QuestionResponse> createQuestion(Question question) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(questionServiceUri);

        RestTemplate template = restTemplateBuilder.build();

        return template.postForEntity(builder.build().toUriString(), question, QuestionResponse.class);
    }

    @Override
    public ResponseEntity<QuestionResponse> updateQuestion(Question question) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(questionServiceUri);

        RestTemplate template = restTemplateBuilder.build();

        HttpEntity<Question> requestEntity = new HttpEntity<>(question, null);


        return template.exchange(builder.build().toUri(), HttpMethod.PUT, requestEntity, QuestionResponse.class);
    }

    @Override
    public ResponseEntity deleteQuestion(String id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(questionServiceUri).pathSegment(id);

        RestTemplate template = restTemplateBuilder.build();

        HttpEntity requestEntity = new HttpEntity(null, null);

        return template.exchange(builder.build().toUri(), HttpMethod.DELETE, requestEntity, Object.class);
    }
}
