package com.example.springSecurityApplication.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Manuscript {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false, columnDefinition = "text", unique = true)
    @NotEmpty(message = "Title cannot be empty.")
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "text")
    @NotEmpty(message = "Description cannot be empty.")
    private String description;

    @Column(name = "dating", nullable = false)
    @Min(value = 4, message = "Dating cannot be negative. The earliest manuscripts date back to the 4th century. ")
    private int dating;

    @Column(name = "collection", nullable = false)
    @NotEmpty(message = "Collection cannot be empty.")
    private String collection;

    @Column(name = "publications", nullable = false, columnDefinition = "text")
    @NotEmpty(message = "If there are no any other publications 'TOCHMAC' must be mentioned")
    private String publications;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "manuscript")
    private List<Image> imageList = new ArrayList<>();
    private LocalDateTime dateTime;

    @ManyToOne(optional = false)
    private Category category;

    // Будем заполнять дату и время при создании объекта класса:
    @PrePersist
    private void init() {
        dateTime = LocalDateTime.now();
    }

    @ManyToMany()
    @JoinTable(name = "manuscript_favourites", joinColumns = @JoinColumn(name = "manuscript_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> personList;

    @OneToMany(mappedBy = "manuscript")
    private List<Selection> selectionList;

    // Метод по добавлению фотографий в лист к текущему продукту:
    public void addImageToManuscript(Image image) {
        image.setManuscript(this);
        imageList.add(image);
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDating() {
        return dating;
    }

    public void setDating(int dating) {
        this.dating = dating;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getPublications() {
        return publications;
    }

    public void setPublications(String publications) {
        this.publications = publications;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

