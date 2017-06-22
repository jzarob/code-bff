package com.e451.rest.gateways.impl;

import com.e451.rest.domains.assessment.Assessment;
import com.e451.rest.domains.assessment.AssessmentResponse;
import com.e451.rest.gateways.AssessmentServiceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by j747951 on 6/15/2017.
 */
@Service
public class AssessmentServiceGatewayImpl implements AssessmentServiceGateway {

    private final String assessmentServiceUri;
    private final RestTemplate restTemplate;

    @Autowired
    public AssessmentServiceGatewayImpl(@Value("${service-uri}") String assessmentServiceUri,
                                        RestTemplate restTemplate) {
        this.assessmentServiceUri = assessmentServiceUri + "/assessments";
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<AssessmentResponse> getAssessments() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(assessmentServiceUri);
        return restTemplate.getForEntity(builder.build().toUriString(), AssessmentResponse.class);
    }

    @Override
    public ResponseEntity<AssessmentResponse> createAssessment(Assessment assessment) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(assessmentServiceUri);
        return restTemplate.postForEntity(builder.build().toUriString(), assessment, AssessmentResponse.class);
    }
}
