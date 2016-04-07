package com.fortum.nokid.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by yuriy on 07.04.16.
 */

@Entity
@Table(name = "Excercises")
public class Excercise {

    @NotNull
    @Id private long id;

    @NotNull
    private String aufgabeText;

    @NotNull
    private String beleg;

    @NotNull
    @OneToOne(cascade= CascadeType.PERSIST)
    private Konto rightSoll;

    @NotNull
    @OneToOne(cascade=CascadeType.PERSIST)
    private Konto rightHaben;

    @NotNull
    @ElementCollection
    private List<Konto> falseSollOderHaben;

    Excercise() { }

    public Excercise(long id, String aufgabeText, String beleg, Konto rightSoll, Konto rightHaben, List<Konto> falseSollOderHaben) {
        this.id = id;
        this.aufgabeText = aufgabeText;
        this.beleg = beleg;
        this.rightSoll = rightSoll;
        this.rightHaben = rightHaben;
        this.falseSollOderHaben = falseSollOderHaben;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAufgabeText() {
        return aufgabeText;
    }

    public void setAufgabeText(String aufgabeText) {
        this.aufgabeText = aufgabeText;
    }

    public String getBeleg() {
        return beleg;
    }

    public void setBeleg(String beleg) {
        this.beleg = beleg;
    }

    public Konto getRightSoll() {
        return rightSoll;
    }

    public void setRightSoll(Konto rightSoll) {
        this.rightSoll = rightSoll;
    }

    public Konto getRightHaben() {
        return rightHaben;
    }

    public void setRightHaben(Konto rightHaben) {
        this.rightHaben = rightHaben;
    }

    public List<Konto> getFalseSollOderHaben() {
        return falseSollOderHaben;
    }

    public void setFalseSollOderHaben(List<Konto> falseSollOderHaben) {
        this.falseSollOderHaben = falseSollOderHaben;
    }
}
