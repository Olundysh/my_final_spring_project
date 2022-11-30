package com.example.springSecurityApplication.models;

import javax.persistence.*;

@Entity
@Table(name = "manuscript_cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "person_id")
    private int personId;

    @Column(name = "manuscript_id")
    private int manuscriptId;


    public Cart(int personId, int manuscriptId) {
        this.personId = personId;
        this.manuscriptId = manuscriptId;
    }

    public Cart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getManuscriptId() {
        return manuscriptId;
    }

    public void setManuscriptId(int manuscriptId) {
        this.manuscriptId = manuscriptId;
    }
}

