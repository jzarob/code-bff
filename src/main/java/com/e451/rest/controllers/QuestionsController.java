package com.e451.rest.controllers;

import com.e451.rest.domains.language.LanguageResponse;
import com.e451.rest.domains.question.Question;
import com.e451.rest.domains.question.QuestionResponse;
import com.e451.rest.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by e384873 on 6/9/2017.
 */
@RestController
@CrossOrigin
@RequestMapping("/questions")
public class QuestionsController {

    private QuestionService questionService;

    @Autowired
    public QuestionsController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<QuestionResponse> getQuestions() {
        return questionService.getQuestions();
    }

    @GetMapping(params = {"page", "size", "property"})
    public ResponseEntity<QuestionResponse> getQuestions(@RequestParam("page") int page,
                                                         @RequestParam("size") int size,
                                                         @RequestParam("property") String property) {
        return questionService.getQuestions(page, size, property);
    }

    @PutMapping
    public ResponseEntity<QuestionResponse> updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question);
    }

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(@RequestBody Question question) {
        return questionService.createQuestion(question);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestion(@PathVariable String id) {
        return questionService.getQuestion(id);
    }

    @GetMapping("/languages")
    public ResponseEntity<LanguageResponse> getLanguages() {
        return questionService.getLanguages();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteQuestion(@PathVariable String id) {
        return questionService.deleteQuestion(id);
    }
}
