package com.example.thinquizzer;

public class MultipleChoiceQuestion {
    private String question;
    private String correct_answer;
    private String[] answers=new String[2];

    public String getQuestion() {
        return question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }
}
