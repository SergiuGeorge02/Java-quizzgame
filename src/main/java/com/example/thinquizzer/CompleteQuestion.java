package com.example.thinquizzer;

public class CompleteQuestion {
    private String question;
    private String correct_answer;
    private String hint;
    private boolean hintUsed;

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
    public String getHint(){
        return hint;
    }
    public void setHint(String s){
        this.hint=s;
    }
    public String getCorrect_answer() {
        return correct_answer;
    }
    public String toString(){
        return question+" with answer: "+correct_answer;
    }
    public boolean isHintUsed() {
        return hintUsed;
    }

    public void setHintUsed(boolean hintUsed) {
        this.hintUsed = hintUsed;
    }

}
