package com.fortum.nokid.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by yuriy on 10/08/16.
 */

@Entity
@Table(name = "lectures")
public class Lecture implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "lecture")
    private List<QuestionLecture> questionLectures;

    public Lecture() {
    }

    public Lecture(String name) {
        this.name = name;
    }

    public Lecture(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<QuestionLecture> getQuestionLectures() {
        return questionLectures;
    }

    private void setQuestionLectures(List<QuestionLecture> questionLectures) {
        this.questionLectures = questionLectures;
    }
}
