package com.fortum.nokid.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by yuriy on 07.04.16.
 */

@Entity
@Table(name = "Konten")
public class Konto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long kontoNr;

    @NotNull
    private String name;

    private String art;

    Konto() { }

    public Konto(String name, String art) {
        this.name = name;
        this.art = art;
    }

    public long getKontoNr() {
        return kontoNr;
    }

    public void setKontoNr(long kontoNr) {
        this.kontoNr = kontoNr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }
}
