package com.e451.rest.controllers;

import com.e451.rest.domains.assessment.Assessment;
import com.e451.rest.domains.assessment.AssessmentState;
import com.e451.rest.domains.assessment.QuestionAnswer;
import com.e451.rest.domains.assessment.QuestionAnswerResponse;
import com.e451.rest.domains.assessment.events.*;
import com.e451.rest.services.AssessmentService;
import com.e451.rest.services.QuestionAnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by j747951 on 6/29/2017.
 */
@Controller
@MessageMapping("/assessment/{guid}")
public class AssessmentsSocketController {

    private QuestionAnswerService questionAnswerService;
    private AssessmentService assessmentService;

    private static final Logger LOG = LoggerFactory.getLogger(AssessmentsSocketController.class);

    @Autowired
    public AssessmentsSocketController(QuestionAnswerService questionAnswerService,
                                       AssessmentService assessmentService) {
        this.questionAnswerService = questionAnswerService;
        this.assessmentService = assessmentService;
    }


    @MessageMapping("/connect")
    @SendTo("/topic/assessment/{guid}/connect")
    public ConnectEvent connect(@DestinationVariable("guid") String assessmentGuid,
                                ConnectEvent event) {
        LOG.info("received connect event for assessment " + assessmentGuid);
        return event;
    }

    @MessageMapping("/new-question")
    @SendTo("/topic/assessment/{guid}/new-question")
    public NewQuestionEvent newQuestion(@DestinationVariable("guid") String assessmentGuid, NewQuestionEvent event) {

        LOG.info("received new-question event for assessment " + assessmentGuid);

        // create a new QuestionResponseId for the assessment
        ResponseEntity<QuestionAnswerResponse> questionAnswer = questionAnswerService.createQuestionAnswer(new QuestionAnswer(),
                assessmentGuid);
        // set the event's questionResponseId to be the one that was just created
        event.setQuestionResponseId(questionAnswer.getBody().getQuestionAnswers().get(0).getQuestionResponseId());
        // forward the event.
        return event;
    }

    @MessageMapping("/answer-question")
    @SendTo("/topic/assessment/{guid}/answer-question")
    public AnswerQuestionEvent answerQuestion(@DestinationVariable("guid") String assessmentGuid,
                                              AnswerQuestionEvent event) {

        LOG.info("received answer-question event for assessment " + assessmentGuid);

        QuestionAnswer questionAnswer = new QuestionAnswer(event.getTitle(), event.getBody(), event.getAnswer(),
                event.getQuestionResponseId());
        questionAnswerService.updateQuestionAnswer(questionAnswer, assessmentGuid);
        // Populate additional fields on the question response
        return event;
    }

    @MessageMapping("/end-assessment")
    @SendTo("/topic/assessment/{guid}/end-assessment")
    public EndAssessmentEvent endAssessment(@DestinationVariable("guid") String assessmentGuid,
                                            EndAssessmentEvent event) {

        LOG.info("received end-assessment event for assessment " + assessmentGuid);

        Assessment current = assessmentService.getAssessmentByGuid(assessmentGuid).getBody().getAssessments().get(0);
        current.setState(AssessmentState.NOTES);
        assessmentService.updateAssessment(current);

        return event;
    }

    @MessageMapping("/disconnect")
    @SendTo("/topic/assessment/{guid}/disconnect")
    public DisconnectEvent disconnect(@DestinationVariable("guid") String assessmentGuid,
                                      DisconnectEvent event)    {
        LOG.info("received disconnect event for assessment " + assessmentGuid);
        return event;
    }

}
