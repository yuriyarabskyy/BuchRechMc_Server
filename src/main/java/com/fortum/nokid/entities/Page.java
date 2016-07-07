package com.fortum.nokid.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by yuriy on 07.04.16.
 */

@Entity
@IdClass(PagePK.class)
@Table(name = "Pages")
public class Page {

    @Id
    private long vorlesungId;

    @Id
    @NotNull
    private int from;
    @Id
    @NotNull
    private int to;

    Page() { }

    public Page(long vorlesungId, int from, int to) {
        this.vorlesungId = vorlesungId;
        this.from = from;
        this.to = to;
    }

    public Page(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public long getVorlesungId() {
        return vorlesungId;
    }

    public void setVorlesungId(long vorlesungId) {
        this.vorlesungId = vorlesungId;
    }
}
