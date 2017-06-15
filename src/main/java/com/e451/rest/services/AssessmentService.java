package com.e451.rest.services;

import com.e451.rest.domains.assessment.Assessment;
import com.e451.rest.domains.assessment.AssessmentResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by j747951 on 6/15/2017.
 */
public interface AssessmentService {
    ResponseEntity<AssessmentResponse> getAssessments();
    ResponseEntity<AssessmentResponse> createAssessment(Assessment assessment);
}
