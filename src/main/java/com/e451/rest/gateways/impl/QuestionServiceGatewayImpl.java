package com.e451.rest.gateways.impl;

import com.e451.rest.domains.language.LanguageResponse;
import com.e451.rest.domains.question.Question;
import com.e451.rest.domains.question.QuestionResponse;
import com.e451.rest.gateways.QuestionServiceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final RestTemplate restTemplate;

    @Autowired
    public QuestionServiceGatewayImpl(@Value("${service-uri}") String questionServiceUri,
                                      RestTemplate restTemplate) {
        this.questionServiceUri = questionServiceUri + "/questions";
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<QuestionResponse> getQuestions() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(questionServiceUri);
        return restTemplate.getForEntity(builder.build().toUriString(), QuestionResponse.class);
    }

    @Override
    public ResponseEntity<QuestionResponse> getQuestions(int page, int size, String property) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(questionServiceUri)
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("property", property);
        return restTemplate.getForEntity(builder.build().toUriString(), QuestionResponse.class);
    }

    @Override
    public ResponseEntity<QuestionResponse> getQuestion(String id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(questionServiceUri).pathSegment(id);
        return restTemplate.getForEntity(builder.build().toUriString(), QuestionResponse.class);
    }

    @Override
    public ResponseEntity<LanguageResponse> getLanguages() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(questionServiceUri).pathSegment("languages");
        return restTemplate.getForEntity(builder.build().toUriString(), LanguageResponse.class);
    }

    @Override
    public ResponseEntity<QuestionResponse> createQuestion(Question question) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(questionServiceUri);
        return restTemplate.postForEntity(builder.build().toUriString(), question, QuestionResponse.class);
    }

    @Override
    public ResponseEntity<QuestionResponse> updateQuestion(Question question) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(questionServiceUri);
        HttpEntity<Question> requestEntity = new HttpEntity<>(question, null);

        return restTemplate.exchange(builder.build().toUri(), HttpMethod.PUT, requestEntity, QuestionResponse.class);
    }

    @Override
    public ResponseEntity deleteQuestion(String id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(questionServiceUri).pathSegment(id);

        HttpEntity requestEntity = new HttpEntity(null, null);

        return restTemplate.exchange(builder.build().toUri(), HttpMethod.DELETE, requestEntity, Object.class);
    }
}
