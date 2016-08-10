package com.fortum.nokid.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yuriy on 10/08/16.
 */

@Entity
@Table(name = "questions_lectures")
public class QuestionLecture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Column(name = "from_page")
    private int fromPage;

    @Column(name = "to_page")
    private int toPage;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public QuestionLecture() {
    }

    public QuestionLecture(Lecture lecture, int fromPage, int toPage, Question question) {
        this.lecture = lecture;
        this.fromPage = fromPage;
        this.toPage = toPage;
        this.question = question;
    }

    private int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
