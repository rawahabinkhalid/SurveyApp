package com.matzsolutions.surveyapp.Retailor.ui.Support;

public class SurveyAnswerModel {
    private int OptionID;
    private int SurveyID;
    private int QuestionID;
    private String Options;

    public SurveyAnswerModel(int optionID, int surveyID, int questionID, String options) {
        OptionID = optionID;
        SurveyID = surveyID;
        QuestionID = questionID;
        Options = options;
    }

    public int getOptionID() {
        return OptionID;
    }

    public void setOptionID(int optionID) {
        OptionID = optionID;
    }

    public int getSurveyID() {
        return SurveyID;
    }

    public void setSurveyID(int surveyID) {
        SurveyID = surveyID;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int questionID) {
        QuestionID = questionID;
    }

    public String getOptions() {
        return Options;
    }

    public void setOptions(String options) {
        Options = options;
    }
}
