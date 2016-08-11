package com.fortum.nokid.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="questions")
public class Question implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "content")
    private String content;

    @ElementCollection
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<Answer> possibleAnswers;

    @Column(name="topic")
    private String topic;

    @Column(name="chapter")
    private String chapter;

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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
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

}