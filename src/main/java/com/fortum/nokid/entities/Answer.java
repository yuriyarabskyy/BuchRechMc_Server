package com.fortum.nokid.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by yuriy on 10/08/16.
 */

@Entity
@Table(name = "answers")
public class Answer implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private Question question;

    @Id
    @Column(name = "answer_id")
    private int answerId;

    @Column(name = "answer")
    private String answer;

    @OneToMany(mappedBy = "givenAnswer")
    private List<UserQuestion> userQuestions;

    public Answer() { }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private List<UserQuestion> getUserQuestions() {
        return userQuestions;
    }

    private void setUserQuestions(List<UserQuestion> userQuestions) {
        this.userQuestions = userQuestions;
    }
}
