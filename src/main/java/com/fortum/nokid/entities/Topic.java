package com.fortum.nokid.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by yuriy on 13/08/16.
 */

@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @Column(name = "chapter")
    private int chapter;

    @Column(name = "topic")
    private String topic;

    public Topic() { }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
