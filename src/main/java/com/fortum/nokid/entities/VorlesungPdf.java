package com.fortum.nokid.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by yuriy on 07.04.16.
 */

@Entity
@Table(name = "Vorlesungen")
public class VorlesungPdf {

    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id private long id;

    @NotNull
    private String name;

    private byte[] content = null;

    private int questionsPageNumber;


    //instead of this, I defined a vorlesungID in the Page class
    /*
    @ElementCollection
    private List<Page> pages;
*/
    VorlesungPdf() { }

    public VorlesungPdf(long id, String name, int questionsPageNumber) {
        this.id = id;
        this.name = name;
        this.questionsPageNumber = questionsPageNumber;
    }

    public VorlesungPdf(long id, String name) {

        this.id = id;
        this.name = name;
    }

    public VorlesungPdf(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    public byte[] getContent() { return content; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public void setContent(byte[] content) { this.content = content; }

    public int getQuestionsPageNumber() {
        return questionsPageNumber;
    }

    public void setQuestionsPageNumber(int questionsPageNumber) {
        this.questionsPageNumber = questionsPageNumber;
    }

  /*  public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
    */
}
