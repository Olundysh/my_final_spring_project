package com.example.springSecurityApplication.models;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Login cannot be empty.")
    @Size(min = 5, max = 20, message = "Login must be from 5 to 20 symbols.")
    @Column(name = "login")
    private String login;

    @NotEmpty(message = "Password cannot be empty.")
    @Column(name = "password")
    @Max(value = 20, message = "Password must be less then 20 symbols.")
    // Проверка с регулярным выражением временно отключена.
//    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Password must contain not less than eight characters including uppercase and lowercase as well as at least one digit and special symbol.")
    private String password;

    @Column(name = "role")
    private String role;

    @ManyToMany()
    @JoinTable(name = "manuscript_favourites", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "manuscript_id"))
    private List<Manuscript> manuscript;

    @OneToMany(mappedBy = "person")
    private List<Selection> selectionList;

    public Person() {

    }

    public Person(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
