package com.example.springSecurityApplication.models;

import com.example.springSecurityApplication.enumm.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String number;

    @ManyToOne(optional = false)
    private Manuscript manuscript;

    @ManyToOne(optional = false)
    private Person person;

    private int count;
    private float price;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Order() {
    }

    public Order(String number, Manuscript manuscript, Person person, int count, float price, Status status) {
        this.number = number;
        this.manuscript = manuscript;
        this.person = person;
        this.count = count;
        this.price = price;
        this.status = status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}

