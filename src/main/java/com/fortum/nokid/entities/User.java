package com.fortum.nokid.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by yuriy on 06.04.16.
 */


@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @OneToMany(mappedBy = "user")
    private Collection<UserQuestion> answeredQuestions;

    public User() { }

    public User(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public User(String lastName, String firstName, Collection<UserQuestion> answeredQuestions) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.answeredQuestions = answeredQuestions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Collection<UserQuestion> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(Collection<UserQuestion> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }
}
