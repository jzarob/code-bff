package com.e451.rest.services.impl;

import com.e451.rest.domains.assessment.Assessment;
import com.e451.rest.domains.assessment.AssessmentResponse;
import com.e451.rest.domains.assessment.AssessmentStateResponse;
import com.e451.rest.gateways.AssessmentServiceGateway;
import com.e451.rest.services.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by j747951 on 6/15/2017.
 */
@Service
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentServiceGateway assessmentServiceGateway;

    @Autowired
    public AssessmentServiceImpl(AssessmentServiceGateway assessmentServiceGateway) {
        this.assessmentServiceGateway = assessmentServiceGateway;
    }

    @Override
    public ResponseEntity<AssessmentResponse> getAssessments() {
        return assessmentServiceGateway.getAssessments();
    }

    @Override
    public ResponseEntity<AssessmentResponse> getAssessments(int page, int size, String property) {
        return assessmentServiceGateway.getAssessments(page, size, property);
    }

    @Override
    public ResponseEntity<AssessmentResponse> getAssessmentByGuid(String guid) { return assessmentServiceGateway.getAssessmentByGuid(guid); }

    @Override
    public ResponseEntity<AssessmentStateResponse> getAssessmentStateByGuid(String guid) {
        return assessmentServiceGateway.getAssessmentStateByGuid(guid);
    }

    @Override
    public ResponseEntity<AssessmentResponse> createAssessment(Assessment assessment) {
        return assessmentServiceGateway.createAssessment(assessment);
    }

    @Override
    public ResponseEntity<AssessmentResponse> updateAssessment(Assessment assessment) {
        return assessmentServiceGateway.updateAssessment(assessment);
    }

}
