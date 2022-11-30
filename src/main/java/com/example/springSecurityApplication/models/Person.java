package com.example.springSecurityApplication.models;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Логин не может быть пустым")
    @Size(min = 5, max = 100, message = "Логин должен быть от 5 до 100 символов")
    @Column(name = "login")
    private String login;

    @NotEmpty(message = "Пароль не может быть пустым")
//    @Size(min = 5, max = 100, message = "Паро должен быть от 5 до 100 символов")
    @Column(name = "password")
//    @Max(value = 100, message = "Пароль не может быть больше 100 символов")
//    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Пароль должен содержать не менее 8 символов, хотя бы одну цифру, спец символ, букву в верхнем регистре и в нижнем регистре")
    private String password;

    @Column(name = "role")
    private String role;

    @ManyToMany()
    @JoinTable(name = "manuscript_cart", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "manuscript_id"))
    private List<Manuscript> manuscript;

    @OneToMany(mappedBy = "person")
    private List<Order> orderList;

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
