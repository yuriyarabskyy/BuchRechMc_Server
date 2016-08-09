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

    //private int questionsPageNumber = 0;


    //instead of this, I defined a vorlesungID in the Page class
    /*
    @ElementCollection
    private List<Page> pages;
*/
    public VorlesungPdf() { }

    public VorlesungPdf(long id, String name) {
        this.id = id;
        this.name = name;
        //this.questionsPageNumber = questionsPageNumber;
    }


    public VorlesungPdf(String name) {
        this.name = name;
       // this.questionsPageNumber = questionsPageNumber;
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

    public void setName(String name) { this.name = name; }

    //public int getQuestionsPageNumber() { return questionsPageNumber; }

    //public void setQuestionsPageNumber(int questionsPageNumber) { this.questionsPageNumber = questionsPageNumber; }

  /*  public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
    */
}
