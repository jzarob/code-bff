package com.e451.rest.controllers;

import com.e451.rest.domains.language.LanguageResponse;
import com.e451.rest.domains.question.Question;
import com.e451.rest.domains.question.QuestionResponse;
import com.e451.rest.services.QuestionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by l659598 on 6/12/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class QuestionsControllerTest {

    private QuestionsController questionsController;

    @Mock
    private QuestionService questionService;

    private List<Question> questions;
    private List<String> languages;

    @Before
    public void setup() {
        this.questionsController = new QuestionsController(questionService);
        questions = Arrays.asList(
          new Question("1", "q1", "a1", "t1", 5),
          new Question("2", "q2", "a2", "t2", 3),
          new Question("3", "q3", "a3", "t3", 3)
        );

        languages = Arrays.asList("Java", "Python", "SQL");
    }

    @Test
    public void whenGetQuestions_returnListOfQuestions() {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setQuestions(questions);

        ResponseEntity<QuestionResponse> responseEntity = ResponseEntity.ok(questionResponse);

        when(questionService.getQuestions()).thenReturn(responseEntity);

        ResponseEntity<QuestionResponse> response = questionsController.getQuestions();

        Assert.assertEquals(3, response.getBody().getQuestions().size());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenGetQuestionsPageable_returnListOfQuestions() {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setQuestions(questions);
        questionResponse.setPaginationTotalElements((long) questions.size());

        ResponseEntity<QuestionResponse> responseEntity = ResponseEntity.ok(questionResponse);

        when(questionService.getQuestions(0, 20, "title")).thenReturn(responseEntity);

        ResponseEntity<QuestionResponse> response = questionsController.getQuestions(0, 20, "title");

        Assert.assertEquals(this.questions.size(), response.getBody().getQuestions().size());
        Assert.assertEquals(this.questions.size(), (long) response.getBody().getPaginationTotalElements());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenSearchQuestions_returnListOfQuestions() {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setQuestions(questions);
        questionResponse.setPaginationTotalElements((long) questions.size());

        ResponseEntity<QuestionResponse> responseEntity = ResponseEntity.ok(questionResponse);

        when(questionService
                .searchQuestions(any(Integer.class), any(Integer.class), any(String.class), any(String.class)))
                .thenReturn(responseEntity);

        ResponseEntity<QuestionResponse> response = questionsController.searchQuestions(0, 20, "title", "search");

        Assert.assertEquals(this.questions.size(), response.getBody().getQuestions().size());
        Assert.assertEquals(this.questions.size(), (long) response.getBody().getPaginationTotalElements());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenGetLanguages_returnListOfLanguages() {
        LanguageResponse languageResponse = new LanguageResponse();
        languageResponse.setLanguages(languages);

        ResponseEntity<LanguageResponse> responseEntity = ResponseEntity.ok(languageResponse);

        when(questionService.getLanguages()).thenReturn(responseEntity);

        ResponseEntity<LanguageResponse> response = questionsController.getLanguages();

        Assert.assertEquals(3, response.getBody().getLanguages().size());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenUpdateQuestion_returnUpdatedQuestion() {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setQuestions(Arrays.asList(questions.get(0)));

        ResponseEntity<QuestionResponse> responseEntity = ResponseEntity.status(HttpStatus.ACCEPTED).body(questionResponse);

        when(questionService.updateQuestion(questions.get(0))).thenReturn(responseEntity);

        ResponseEntity<QuestionResponse> response = questionsController.updateQuestion(questions.get(0));

        Assert.assertEquals(response.getBody().getQuestions().get(0), questions.get(0));
        Assert.assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void whenCreateQuestion_returnNewQuestion() {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setQuestions(Arrays.asList(questions.get(0)));

        ResponseEntity<QuestionResponse> responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(questionResponse);

        when(questionService.createQuestion(questions.get(0))).thenReturn(responseEntity);

        ResponseEntity<QuestionResponse> response = questionsController.createQuestion(questions.get(0));

        Assert.assertEquals(questions.get(0), response.getBody().getQuestions().get(0));
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void whenGetQuestion_returnSingleQuestion() {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setQuestions(Arrays.asList(questions.get(0)));

        ResponseEntity<QuestionResponse> responseEntity = ResponseEntity.ok(questionResponse);

        when(questionService.getQuestion("1")).thenReturn(responseEntity);

        ResponseEntity<QuestionResponse> response = questionsController.getQuestion("1");

        Assert.assertEquals(1, response.getBody().getQuestions().size());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void whenDeleteQuestion_returnNoContent() {
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        when(questionService.deleteQuestion("1")).thenReturn(responseEntity);

        ResponseEntity response = questionsController.deleteQuestion("1");

        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
