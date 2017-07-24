package com.e451.rest.gateways;

import com.e451.rest.domains.assessment.Assessment;
import com.e451.rest.domains.assessment.AssessmentResponse;
import com.e451.rest.domains.assessment.AssessmentStateResponse;
import org.springframework.http.ResponseEntity;

/**
 * Created by j747951 on 6/15/2017.
 */
public interface AssessmentServiceGateway {
    ResponseEntity<AssessmentResponse> getAssessments();
    ResponseEntity<AssessmentResponse> getAssessments(int page, int size, String property);
    ResponseEntity<AssessmentResponse> getAssessmentByGuid(String guid);
    ResponseEntity<AssessmentStateResponse> getAssessmentStateByGuid(String guid);
    ResponseEntity<AssessmentResponse> searchAssessments(int page, int size, String property, String searchString);
    ResponseEntity<AssessmentResponse> createAssessment(Assessment assessment);
    ResponseEntity<AssessmentResponse> updateAssessment(Assessment assessment);
}
