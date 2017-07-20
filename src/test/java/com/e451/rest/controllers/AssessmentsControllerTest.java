package com.e451.rest.controllers;

import com.e451.rest.domains.assessment.Assessment;
import com.e451.rest.domains.assessment.AssessmentResponse;
import com.e451.rest.domains.assessment.AssessmentState;
import com.e451.rest.domains.assessment.AssessmentStateResponse;
import com.e451.rest.services.AssessmentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by j747951 on 6/15/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AssessmentsControllerTest {

    private AssessmentsController assessmentsController;

    @Mock
    private AssessmentService assessmentService;

    private List<Assessment> assessments;

    @Before
    public void setup() {
        assessmentsController = new AssessmentsController(assessmentService);
        assessments = Arrays.asList(
                new Assessment("1", "fn1", "ln1", "test1@test.com", new Date()),
                new Assessment("2", "fn2", "ln2", "test2@test.com", new Date()),
                new Assessment("3", "fn3", "ln3", "test3@test.com", new Date())
        );
    }

    @Test
    public void whenGetAssessments_returnListOfAssessments() {
        AssessmentResponse assessmentResponse = new AssessmentResponse();
        assessmentResponse.setAssessments(assessments);

        ResponseEntity<AssessmentResponse> responseEntity = ResponseEntity.ok(assessmentResponse);

        when(assessmentService.getAssessments()).thenReturn(responseEntity);

        ResponseEntity<AssessmentResponse> response = assessmentsController.getAssessments();

        Assert.assertEquals(3, response.getBody().getAssessments().size());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenGetAssessmentsPageable_returnListOfAssessments() {

         AssessmentResponse assessmentResponse = new AssessmentResponse();
         assessmentResponse.setAssessments(assessments);
         assessmentResponse.setPaginationTotalElements((long) assessments.size());

         ResponseEntity<AssessmentResponse> responseEntity = ResponseEntity.ok(assessmentResponse);

         when(assessmentService.getAssessments(0, 20, "title")).thenReturn(responseEntity);

         ResponseEntity<AssessmentResponse> response = assessmentsController.getAssessments(0, 20, "title");

         Assert.assertEquals(this.assessments.size(), response.getBody().getAssessments().size());
         Assert.assertEquals(this.assessments.size(), (long) response.getBody().getPaginationTotalElements());
         Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenGetAssessmentByGuid_returnListOfSingleAssessment() {
        AssessmentResponse assessmentResponse = new AssessmentResponse();
        assessmentResponse.setAssessments(Arrays.asList(assessments.get(0)));

        ResponseEntity<AssessmentResponse> responseEntity = ResponseEntity.ok(assessmentResponse);

        when(assessmentService.getAssessmentByGuid("1")).thenReturn(responseEntity);

        ResponseEntity<AssessmentResponse> response = assessmentsController.getAssessmentByGuid("1");

        Assert.assertEquals(1, response.getBody().getAssessments().size());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenGetAssessmentStateByGuid_returnAssessmentState() {
        AssessmentStateResponse assessmentStateResponse = new AssessmentStateResponse();
        assessmentStateResponse.setState(AssessmentState.NOTES);

        ResponseEntity<AssessmentStateResponse> responseEntity = ResponseEntity.ok(assessmentStateResponse);

        when(assessmentService.getAssessmentStateByGuid("1")).thenReturn(responseEntity);

        ResponseEntity<AssessmentStateResponse> response = assessmentsController.getAssessmentStateByGuid("1");

        Assert.assertEquals(AssessmentState.NOTES, response.getBody().getState());
    }
    
    @Test
    public void whenCreateAssessment_returnNewAssessment() {
        AssessmentResponse assessmentResponse = new AssessmentResponse();
        assessmentResponse.setAssessments(Arrays.asList(assessments.get(0)));

        ResponseEntity<AssessmentResponse> responseEntity =
                ResponseEntity.status(HttpStatus.CREATED).body(assessmentResponse);

        when(assessmentService.createAssessment(assessments.get(0))).thenReturn(responseEntity);

        ResponseEntity<AssessmentResponse> response = assessmentsController.createAssessment(assessments.get(0));

        Assert.assertEquals(assessments.get(0), response.getBody().getAssessments().get(0));
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void whenUpdateAssessment_returnUpdatedAssessment() {
        AssessmentResponse assessmentResponse = new AssessmentResponse();
        assessmentResponse.setAssessments(Arrays.asList(assessments.get(0)));

        ResponseEntity<AssessmentResponse> responseEntity = ResponseEntity.status(HttpStatus.ACCEPTED).body(assessmentResponse);

        when(assessmentService.updateAssessment(assessments.get(0))).thenReturn(responseEntity);

        ResponseEntity<AssessmentResponse> response = assessmentsController.updateAssessment(assessments.get(0));

        Assert.assertEquals(response.getBody().getAssessments().get(0), assessments.get(0));
        Assert.assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }
}
