package com.example.springSecurityApplication.models;

import com.example.springSecurityApplication.enumm.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "selection")
public class Selection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String number;

    @ManyToOne(optional = false)
    private Manuscript manuscript;

    @ManyToOne(optional = false)
    private Person person;

    private int count;
    private int dating;

    private LocalDateTime dateTime;

    private Status status;

    // Будем заполнять дату и время при создании объекта класса
    @PrePersist
    private void init(){
        dateTime = LocalDateTime.now();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Manuscript getManuscript() {
        return manuscript;
    }

    public void setManuscript(Manuscript manuscript) {
        this.manuscript = manuscript;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDating() {
        return dating;
    }

    public void setDating(int dating) {
        this.dating = dating;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Selection() {
    }

    public Selection(String number, Manuscript manuscript, Person person, int count, int dating, Status status) {
        this.number = number;
        this.manuscript = manuscript;
        this.person = person;
        this.count = count;
        this.dating = dating;
        this.status = status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}

