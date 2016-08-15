package com.fortum.nokid.entities;


import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="questions")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Question implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<Answer> possibleAnswers;

    @Column(name="chapter")
    private int chapter;

    @Column(name="hint")
    private String hint;

    @Column(name="correct_answer_id")
    private int correctAnswerId;

    @Column(name="is_booking_entry")
    private boolean isBookingEntry;

    @Column(name="from_page")
    private int fromPage;

    @Column(name="to_page")
    private int toPage;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private List<UserQuestion> userQuestions;

    public Question() {
    }

    public Question(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Answer> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<Answer> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public int getChapter() { return chapter; }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getCorrectAnswerId() {
        return correctAnswerId;
    }

    public void setCorrectAnswerId(int correctAnswerId) {
        this.correctAnswerId = correctAnswerId;
    }

    public boolean isBookingEntry() {
        return isBookingEntry;
    }

    public void setBookingEntry(boolean bookingEntry) {
        isBookingEntry = bookingEntry;
    }

    public int getFromPage() {
        return fromPage;
    }

    public void setFromPage(int fromPage) {
        this.fromPage = fromPage;
    }

    public int getToPage() {
        return toPage;
    }

    public void setToPage(int toPage) {
        this.toPage = toPage;
    }

    public List<UserQuestion> getUserQuestions() {
        return userQuestions;
    }

    public void setUserQuestions(List<UserQuestion> userQuestions) {
        this.userQuestions = userQuestions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Question)) return false;
        Question q = (Question)o;
        return q.getId() == id;
    }
}