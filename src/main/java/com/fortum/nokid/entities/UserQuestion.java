package com.fortum.nokid.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yuriy on 10/08/16.
 */

@Entity
@Table(name = "users_questions")
public class UserQuestion implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "tried")
    private boolean tried;

    @Column(name = "correctly_answered")
    private boolean correctlyAnswered;

    @ManyToOne
    @JoinColumn(name = "given_answer_id")
    private Answer givenAnswer;

    public UserQuestion() { }

    public UserQuestion(User user, Question question, boolean tried, boolean correctlyAnswered, Answer givenAnswer) {
        this.user = user;
        this.question = question;
        this.tried = tried;
        this.correctlyAnswered = correctlyAnswered;
        this.givenAnswer = givenAnswer;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isTried() {
        return tried;
    }

    public void setTried(boolean tried) {
        this.tried = tried;
    }

    public boolean isCorrectlyAnswered() {
        return correctlyAnswered;
    }

    public void setCorrectlyAnswered(boolean correctlyAnswered) {
        this.correctlyAnswered = correctlyAnswered;
    }

    public Answer getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(Answer givenAnswer) {
        this.givenAnswer = givenAnswer;
    }
}
