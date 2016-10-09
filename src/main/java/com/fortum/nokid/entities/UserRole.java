package com.fortum.nokid.entities;

import javax.persistence.*;

/**
 * Created by yuriy on 09/10/16.
 */

@Table(name = "user_roles")
@Entity
public class UserRole {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    public UserRole() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
