package com.fortum.nokid.entities;

import javax.persistence.*;
import java.io.Serializable;

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

    @Column(name = "start_chapter")
    private int startChapter;

    @Column(name = "end_chapter")
    private int endChapter;

    public Lecture() {
    }

    public Lecture(String name) {
        this.name = name;
    }

    public Lecture(String name, int startChapter, int endChapter) {
        this.name = name;
        this.startChapter = startChapter;
        this.endChapter = endChapter;
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

    public int getStartChapter() {
        return startChapter;
    }

    public void setStartChapter(int startChapter) {
        this.startChapter = startChapter;
    }

    public int getEndChapter() {
        return endChapter;
    }

    public void setEndChapter(int endChapter) {
        this.endChapter = endChapter;
    }
}
