package com.e451.rest.gateways.impl;

import com.e451.rest.domains.assessment.QuestionAnswer;
import com.e451.rest.domains.assessment.QuestionAnswerResponse;
import com.e451.rest.gateways.QuestionAnswerServiceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by j747951 on 6/29/2017.
 */
@Service
public class QuestionAnswerServiceServiceGatewayImpl implements QuestionAnswerServiceGateway {

    private final String assessmentsUri;
    private final RestTemplate restTemplate;

    @Autowired
    public QuestionAnswerServiceServiceGatewayImpl(@Value("${service-uri}") String serviceUri, RestTemplate restTemplate) {
        this.assessmentsUri = serviceUri + "/assessments";
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<QuestionAnswerResponse> createQuestionAnswerResponse(QuestionAnswer questionAnswer,
                                                                               String assessmentGuid) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(assessmentsUri)
                .pathSegment(assessmentGuid, "answers");
        ResponseEntity responseEntity;

        try {
            responseEntity = restTemplate.postForEntity(builder.build().toString(), questionAnswer,
                    QuestionAnswerResponse.class);
            return ResponseEntity.status(HttpStatus.CREATED).body((QuestionAnswerResponse) responseEntity.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<QuestionAnswerResponse> updateQuestionAnswerResponse(QuestionAnswer questionAnswer,
                                                                       String assessmentGuid) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(assessmentsUri)
                .pathSegment(assessmentGuid, "answers");

        ResponseEntity responseEntity;
        HttpEntity<QuestionAnswer> requestEntity = new HttpEntity<>(questionAnswer, null);

        try {
            responseEntity = restTemplate.exchange(builder.build().toString(), HttpMethod.PUT, requestEntity,
                    QuestionAnswerResponse.class);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body((QuestionAnswerResponse) responseEntity.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
