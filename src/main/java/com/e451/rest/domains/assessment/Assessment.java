package com.e451.rest.domains.assessment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by j747951 on 6/15/2017.
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Assessment {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String notes;
    private String modifiedBy;
    private String createdBy;
    private Date createdDate;
    private Date modifiedDate;
    private String interviewGuid;
    private Date assessmentDate;
    private Double rating;

    @JsonInclude(value=JsonInclude.Include.ALWAYS)
    private AssessmentState state;
    private List<QuestionAnswer> questionAnswers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getInterviewGuid() {
        return interviewGuid;
    }

    public void setInterviewGuid(String interviewGuid) {
        this.interviewGuid = interviewGuid;
    }

    public List<QuestionAnswer> getQuestionAnswers() {
        if(questionAnswers == null) questionAnswers = new ArrayList<>();
        return questionAnswers;
    }

    public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public AssessmentState getState() {
        return state;
    }

    public void setState(AssessmentState state) {
        this.state = state;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(Date assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public static final String CSV_HEADERS = "first_name,last_name,email,notes,rating,assessment_date";

    @JsonIgnore
    public String toCsvRow() {
        return Stream.of(firstName, lastName, email, notes, rating.toString(), assessmentDate != null ? assessmentDate.toString() : "")
                .map(value -> null == value ? "" : value)
                .map(value -> value.replaceAll("\"", "\"\""))
                .map(value -> value.replaceAll("\n", "  "))
                .map(value -> Stream.of("\"", ",").anyMatch(value::contains) ? "\"" + value + "\"" : value)
                .collect(Collectors.joining(","));
    }

    public Assessment(String id, String firstName, String lastName, String email, Date assessmentDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.interviewGuid = UUID.randomUUID().toString();
        this.assessmentDate = assessmentDate;
    }

    public Assessment() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Assessment that = (Assessment) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        return email != null ? email.equals(that.email) : that.email == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
