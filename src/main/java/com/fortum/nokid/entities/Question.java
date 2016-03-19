package com.fortum.nokid.entities;


import java.util.List;

public class Question {
    private String question;
    private List<String> answers;

    public Question(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
