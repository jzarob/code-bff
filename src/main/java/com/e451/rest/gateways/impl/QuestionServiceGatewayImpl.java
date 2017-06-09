package com.e451.rest.gateways.impl;

import com.e451.rest.domains.question.Question;
import com.e451.rest.domains.question.QuestionResponse;
import com.e451.rest.gateways.QuestionServiceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by e384873 on 6/9/2017.
 */
@Service
public class QuestionServiceGatewayImpl implements QuestionServiceGateway {

    private final String questionServiceUri;
    private final RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public QuestionServiceGatewayImpl(@Value("${service-uri}") String questionServiceUri,
                                      RestTemplateBuilder restTemplateBuilder) {
        this.questionServiceUri = questionServiceUri + "/questions";
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public ResponseEntity<QuestionResponse> getQuestions() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(questionServiceUri);

        RestTemplate template = restTemplateBuilder.build();

        return template.getForEntity(builder.build().toUriString(), QuestionResponse.class);
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
    public void updateQuestion(Question question) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(questionServiceUri);

        RestTemplate template = restTemplateBuilder.build();

        template.put(builder.build().toUriString(), question);
    }

    @Override
    public void deleteQuestion(String id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(questionServiceUri).pathSegment(id);

        RestTemplate template = restTemplateBuilder.build();

        template.delete(builder.build().toUriString(), id);
    }
}
