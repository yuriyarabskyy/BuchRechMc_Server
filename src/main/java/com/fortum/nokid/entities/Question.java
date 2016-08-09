package com.fortum.nokid.entities;


import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="Questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String content;

    @ElementCollection
    private List<String> possibleAnswers;

    private int rightAnswerIndex = -1;

    private String thema;

    private String kapitel;

    private String hint;

    private boolean isRightAnswered = false;

    public Question() {}

    public Question(String content) {
        this.content = content;
    }

    public Question(String content, List<String> possibleAnswers, int rightAnswerIndex, String thema, String kapitel, String hint) {
        this.content = content;
        this.possibleAnswers = possibleAnswers;
        this.rightAnswerIndex = rightAnswerIndex;
        this.thema = thema;
        this.kapitel = kapitel;
        this.hint = hint;
    }

    public Question(String content, List<String> possibleAnswers, int rightAnswerIndex, String thema, String kapitel, String hint, boolean isRightAnswered) {
        this.content = content;
        this.possibleAnswers = possibleAnswers;
        this.rightAnswerIndex = rightAnswerIndex;
        this.thema = thema;
        this.kapitel = kapitel;
        this.hint = hint;
        this.isRightAnswered = isRightAnswered;
    }

    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public int getRightAnswerIndex() {
        return rightAnswerIndex;
    }

    public void setRightAnswerIndex(int rightAnswerIndex) {
        this.rightAnswerIndex = rightAnswerIndex;
    }

    public String getThema() {
        return thema;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    public String getKapitel() {
        return kapitel;
    }

    public void setKapitel(String kapitel) {
        this.kapitel = kapitel;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public boolean isRightAnswered() {
        return isRightAnswered;
    }

    public void setRightAnswered(boolean rightAnswered) {
        isRightAnswered = rightAnswered;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
