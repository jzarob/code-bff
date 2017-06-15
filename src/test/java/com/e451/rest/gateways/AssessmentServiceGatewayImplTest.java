package com.e451.rest.gateways;

import com.e451.rest.domains.assessment.Assessment;
import com.e451.rest.domains.assessment.AssessmentResponse;
import com.e451.rest.gateways.impl.AssessmentServiceGatewayImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by j747951 on 6/15/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AssessmentServiceGatewayImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    private AssessmentServiceGateway assessmentServiceGateway;

    private static final String BASE_URI = "fakeUri/assessments";

    @Before
    public void setup() {
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        assessmentServiceGateway = new AssessmentServiceGatewayImpl("fakeUri", restTemplateBuilder);
    }

    @Test
    public void whenGetAssessmentsCalled_thenRestTemplateIsCalled() throws Exception {
        AssessmentResponse assessmentResponse = new AssessmentResponse();
        ResponseEntity<AssessmentResponse> response = ResponseEntity.ok(assessmentResponse);

        when(restTemplate.getForEntity(BASE_URI, AssessmentResponse.class)).thenReturn(response);

        assessmentServiceGateway.getAssessments();

        verify(restTemplate).getForEntity(BASE_URI, AssessmentResponse.class);
    }

    @Test
    public void whenCreateAssessmentCalled_thenRestTemplateIsCalled() throws Exception {
        AssessmentResponse assessmentResponse = new AssessmentResponse();
        final Assessment assessment =
                new Assessment("1", "firstName", "lastName", "test@test.com");
        assessmentResponse.setAssessments(Arrays.asList(assessment));
        ResponseEntity<AssessmentResponse> response = ResponseEntity.ok(assessmentResponse);

        when(restTemplate.postForEntity(BASE_URI, assessment, AssessmentResponse.class)).thenReturn(response);

        assessmentServiceGateway.createAssessment(assessment);

        verify(restTemplate).postForEntity(BASE_URI, assessment, AssessmentResponse.class);
    }

}
