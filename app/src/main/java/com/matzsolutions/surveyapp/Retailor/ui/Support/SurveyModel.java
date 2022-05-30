package com.matzsolutions.surveyapp.Retailor.ui.Support;

public class SurveyModel {
    private int SurveyID;
    private int OrganizationID;
    private String Title;
    private String Deadline;
    private String OrgName;
    private String AnswerID;
    private int IsActive;

    public SurveyModel(int surveyID, int organizationID, String title, String deadline, String orgName, String answerID, int isActive) {
        SurveyID = surveyID;
        OrganizationID = organizationID;
        Title = title;
        Deadline = deadline;
        OrgName = orgName;
        AnswerID = answerID;
        IsActive = isActive;
    }

    public int getSurveyID() {
        return SurveyID;
    }

    public void setSurveyID(int surveyID) {
        SurveyID = surveyID;
    }

    public int getOrganizationID() {
        return OrganizationID;
    }

    public void setOrganizationID(int organizationID) {
        OrganizationID = organizationID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDeadline() {
        return Deadline;
    }

    public void setDeadline(String deadline) {
        Deadline = deadline;
    }

    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String orgName) {
        OrgName = orgName;
    }

    public String getAnswerID() {
        return AnswerID;
    }

    public void setAnswerID(String answerID) {
        AnswerID = answerID;
    }

    public int getIsActive() {
        return IsActive;
    }

    public void setIsActive(int isActive) {
        IsActive = isActive;
    }
}
