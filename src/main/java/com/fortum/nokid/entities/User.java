package com.fortum.nokid.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by yuriy on 06.04.16.
 */


@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    private int score;

    @ElementCollection
    private List<Question> answeredQuestions;

    public User() { }

    public User(String name, int score, List<Question> answeredQuestions) {
        this.name = name;
        this.score = score;
        this.answeredQuestions = answeredQuestions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Question> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(List<Question> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }
}
