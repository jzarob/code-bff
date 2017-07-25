package com.e451.rest.gateways;

import com.e451.rest.domains.assessment.Assessment;
import com.e451.rest.domains.assessment.AssessmentResponse;
import com.e451.rest.domains.assessment.AssessmentStateResponse;
import com.e451.rest.gateways.impl.AssessmentServiceGatewayImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by j747951 on 6/15/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AssessmentServiceGatewayImplTest {

    @Mock
    private RestTemplate restTemplate;

    private AssessmentServiceGateway assessmentServiceGateway;

    private static final String BASE_URI = "fakeUri/assessments";

    @Before
    public void setup() {
        assessmentServiceGateway = new AssessmentServiceGatewayImpl("fakeUri", restTemplate);
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
    public void whenGetAssessmentsPageableCalled_thenRestTemplateIsCalled() throws Exception {
        AssessmentResponse assessmentResponse = new AssessmentResponse();
        ResponseEntity<AssessmentResponse> response = ResponseEntity.ok(assessmentResponse);

        when(restTemplate.getForEntity("fakeUri/assessments?page=0&size=20&property=lastName", AssessmentResponse.class)).thenReturn(response);

        assessmentServiceGateway.getAssessments(0, 20, "lastName");

        verify(restTemplate).getForEntity("fakeUri/assessments?page=0&size=20&property=lastName", AssessmentResponse.class);
    }

    @Test
    public void whenGetAssessmentByGuidCalled_thenRestTemplateIsCalled() throws Exception {
        AssessmentResponse assessmentResponse = new AssessmentResponse();
        ResponseEntity<AssessmentResponse> response = ResponseEntity.ok(assessmentResponse);

        when(restTemplate.getForEntity("fakeUri/assessments/1", AssessmentResponse.class)).thenReturn(response);

        assessmentServiceGateway.getAssessmentByGuid("1");

        verify(restTemplate).getForEntity("fakeUri/assessments/1", AssessmentResponse.class);
    }

    @Test
    public void whenGetAssessmentStateByGuidCalled_thenRestTemplateIsCalled() throws Exception {
        AssessmentStateResponse assessmentStateResponse = new AssessmentStateResponse();
        ResponseEntity<AssessmentStateResponse> response = ResponseEntity.ok(assessmentStateResponse);

        when(restTemplate.getForEntity("fakeUri/assessments/1/status", AssessmentStateResponse.class))
                .thenReturn(response);

        assessmentServiceGateway.getAssessmentStateByGuid("1");

        verify(restTemplate).getForEntity("fakeUri/assessments/1/status", AssessmentStateResponse.class);
    }

    @Test
    public void whenSearchAssessmentsCalled_thenRestTemplateIsCalled() throws Exception {
        AssessmentResponse assessmentResponse = new AssessmentResponse();
        ResponseEntity<AssessmentResponse> response = ResponseEntity.ok(assessmentResponse);

        when(restTemplate.getForEntity("fakeUri/assessments/search?page=0&size=20&property=title&searchString=search", AssessmentResponse.class)).thenReturn(response);

        assessmentServiceGateway.searchAssessments(0,20,"title", "search");

        verify(restTemplate).getForEntity("fakeUri/assessments/search?page=0&size=20&property=title&searchString=search", AssessmentResponse.class);
    }

    @Test
    public void whenCreateAssessmentCalled_thenRestTemplateIsCalled() throws Exception {
        AssessmentResponse assessmentResponse = new AssessmentResponse();
        final Assessment assessment =
                new Assessment("1", "firstName", "lastName", "test@test.com", new Date());
        assessmentResponse.setAssessments(Arrays.asList(assessment));
        ResponseEntity<AssessmentResponse> response = ResponseEntity.ok(assessmentResponse);

        when(restTemplate.postForEntity(BASE_URI, assessment, AssessmentResponse.class)).thenReturn(response);

        assessmentServiceGateway.createAssessment(assessment);

        verify(restTemplate).postForEntity(BASE_URI, assessment, AssessmentResponse.class);
    }
    
    @Test
    public void whenUpdateAssessmentCalled_thenRestTemplateIsCalled() throws Exception {
        AssessmentResponse assessmentResponse = new AssessmentResponse();

        Assessment assessment = new Assessment("1", "firstName2", "lastName2", "test@test.com", new Date());

        assessmentResponse.setAssessments(Arrays.asList(assessment));

        HttpEntity<Assessment> assessmentHttpEntity = new HttpEntity<>(assessment, null);
        ResponseEntity<AssessmentResponse> responseEntity = ResponseEntity.ok(assessmentResponse);


        when(restTemplate.exchange(new URI("fakeUri/assessments"), HttpMethod.PUT, assessmentHttpEntity, AssessmentResponse.class)).thenReturn(responseEntity);

        assessmentServiceGateway.updateAssessment(assessment);

        verify(restTemplate).exchange(new URI("fakeUri/assessments"), HttpMethod.PUT, assessmentHttpEntity, AssessmentResponse.class);
    }
    
    

}
