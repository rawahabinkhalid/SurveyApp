package com.matzsolutions.surveyapp.Retailor.ui.Support;

import java.util.List;

public class SurveyQuestionsModel {
    private int QuestionID;
    private int SurveyID;
    private String Question;
    private String Type;
    private List<SurveyAnswerModel> Answers;
    private String Answer;

    public SurveyQuestionsModel(int questionID, int surveyID, String question, String type, List<SurveyAnswerModel> answers, String answer) {
        QuestionID = questionID;
        SurveyID = surveyID;
        Question = question;
        Type = type;
        Answers = answers;
        Answer = answer;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int questionID) {
        QuestionID = questionID;
    }

    public int getSurveyID() {
        return SurveyID;
    }

    public void setSurveyID(int surveyID) {
        SurveyID = surveyID;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public List<SurveyAnswerModel> getAnswers() {
        return Answers;
    }

    public void setAnswers(List<SurveyAnswerModel> answers) {
        Answers = answers;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
