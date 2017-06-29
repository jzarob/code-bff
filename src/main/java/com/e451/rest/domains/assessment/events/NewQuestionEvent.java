package com.e451.rest.domains.assessment.events;

/**
 * Created by j747951 on 6/29/2017.
 */
public class NewQuestionEvent {
    private String title;
    private String body;
    private String questionResponseId;

    public NewQuestionEvent() {
    }

    public NewQuestionEvent(String title, String body, String questionResponseId) {
        this.title = title;
        this.body = body;
        this.questionResponseId = questionResponseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getQuestionResponseId() {
        return questionResponseId;
    }

    public void setQuestionResponseId(String questionResponseId) {
        this.questionResponseId = questionResponseId;
    }
}
