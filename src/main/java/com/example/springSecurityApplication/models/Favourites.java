package com.example.springSecurityApplication.models;

import javax.persistence.*;

@Entity
@Table(name = "manuscript_favourites")
public class Favourites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "person_id")
    private int personId;

    @Column(name = "manuscript_id")
    private int manuscriptId;


    public Favourites(int personId, int manuscriptId) {
        this.personId = personId;
        this.manuscriptId = manuscriptId;
    }

    public Favourites() {
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

