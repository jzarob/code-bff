package com.e451.rest.controllers;

import com.e451.rest.domains.assessment.Assessment;
import com.e451.rest.domains.assessment.AssessmentResponse;
import com.e451.rest.domains.assessment.AssessmentStateResponse;
import com.e451.rest.services.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by j747951 on 6/15/2017.
 */
@Controller
@RequestMapping("/assessments")
@CrossOrigin
public class AssessmentsController {

    private final AssessmentService assessmentService;

    @Autowired
    public AssessmentsController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping
    public ResponseEntity<AssessmentResponse> getAssessments() {
        return assessmentService.getAssessments();
    }

    @GetMapping(params = {"page", "size", "property"})
    public ResponseEntity<AssessmentResponse> getAssessments(@RequestParam("page") int page,
                                                             @RequestParam("size") int size,
                                                             @RequestParam("property") String property) {
        return assessmentService.getAssessments(page, size, property);
    }

    @GetMapping("/{guid}")
    public ResponseEntity<AssessmentResponse> getAssessmentByGuid(@PathVariable String guid) {
        return assessmentService.getAssessmentByGuid(guid);
    }

    @GetMapping("/{guid}/status")
    public ResponseEntity<AssessmentStateResponse> getAssessmentStateByGuid(@PathVariable("guid") String guid) {
        return assessmentService.getAssessmentStateByGuid(guid);
    }

    @GetMapping(name="/search", params = {"page", "size", "property", "searchString"})
    public ResponseEntity<AssessmentResponse> searchAssessments(@RequestParam("page") int page,
                                                                @RequestParam("size") int size,
                                                                @RequestParam("property") String property,
                                                                @RequestParam("searchString") String searchString) {
        return assessmentService.searchAssessments(page, size, property, searchString);
    }

    @PostMapping
    public ResponseEntity<AssessmentResponse> createAssessment(@RequestBody Assessment assessment) {
        return assessmentService.createAssessment(assessment);
    }

    @PutMapping
    public ResponseEntity<AssessmentResponse> updateAssessment(@RequestBody Assessment assessment) {
        return assessmentService.updateAssessment(assessment);
    }
}
