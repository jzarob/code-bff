package com.e451.rest.services;

import com.e451.rest.domains.assessment.Assessment;
import com.e451.rest.domains.assessment.AssessmentResponse;
import com.e451.rest.domains.assessment.AssessmentStateResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by j747951 on 6/15/2017.
 */
public interface AssessmentService {
    ResponseEntity<AssessmentResponse> getAssessments();
    ResponseEntity<AssessmentResponse> getAssessments(int page, int size, String property);
    ResponseEntity<AssessmentResponse> getAssessmentByGuid(String guid);
    ResponseEntity<AssessmentStateResponse> getAssessmentStateByGuid(String guid);
    ResponseEntity<AssessmentResponse> searchAssessments(int page, int size, String property, String searchString);
    ResponseEntity<AssessmentResponse> createAssessment(Assessment assessment);
    ResponseEntity<AssessmentResponse> updateAssessment(Assessment assessment);

    Stream<String> getAssessmentsCsv();
}
