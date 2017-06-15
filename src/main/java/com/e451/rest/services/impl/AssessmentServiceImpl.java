package com.e451.rest.services.impl;

import com.e451.rest.domains.assessment.Assessment;
import com.e451.rest.domains.assessment.AssessmentResponse;
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
        return null;
    }

    @Override
    public ResponseEntity<AssessmentResponse> createAssessment(Assessment assessment) {
        return null;
    }
}
