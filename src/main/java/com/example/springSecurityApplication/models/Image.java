package com.example.springSecurityApplication.models;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fileName;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Manuscript manuscript;

    public Image(int id, String fileName, Manuscript manuscript) {
        this.id = id;
        this.fileName = fileName;
        this.manuscript = manuscript;
    }

    public Image() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Manuscript getManuscript() {
        return manuscript;
    }

    public void setManuscript(Manuscript manuscript) {
        this.manuscript = manuscript;
    }
}