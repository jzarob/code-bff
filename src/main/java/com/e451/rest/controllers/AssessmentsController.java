package com.e451.rest.controllers;

import com.e451.rest.domains.assessment.Assessment;
import com.e451.rest.domains.assessment.AssessmentResponse;
import com.e451.rest.domains.assessment.AssessmentStateResponse;
import com.e451.rest.services.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.stream.Collectors;

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

    @GetMapping("/csv")
    public void getAssessmentsCsv(HttpServletResponse response) throws IOException {
//        String str = assessmentService.getAssessmentsCsv().collect(Collectors.joining(System.getProperty("line.separator")));
        response.setContentType("text/csv;charset=utf-8");
        response.setHeader("Content-Disposition","attachement; filename=\"assessments.csv\"");
        ServletOutputStream writer = response.getOutputStream();
        assessmentService.getAssessmentsCsv().forEach(row -> {
            try {
                writer.print(row);
                writer.print(System.getProperty("line.separator"));
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });
        writer.flush();
        writer.close();
    }

    @GetMapping("/{guid}")
    public ResponseEntity<AssessmentResponse> getAssessmentByGuid(@PathVariable String guid) {
        return assessmentService.getAssessmentByGuid(guid);
    }

    @GetMapping("/{guid}/status")
    public ResponseEntity<AssessmentStateResponse> getAssessmentStateByGuid(@PathVariable("guid") String guid) {
        return assessmentService.getAssessmentStateByGuid(guid);
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
