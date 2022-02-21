package com.epam.models;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "clients")
@EnableAutoConfiguration
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true, length = 64, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, length = 64, nullable = false)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "role", nullable = false, columnDefinition = "integer default 0")
    private Integer role;

    @OneToMany(mappedBy = "client", targetEntity = CreditCard.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditCard> creditCards;

    protected Client() {
    }

    public Client(String login, String password, String email, String firstName, String lastName, Integer role) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.creditCards = new ArrayList<>();
    }

    public Client(Long id, String login, String password, String email, String firstName, String lastName, Integer role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.creditCards = new ArrayList<>();
    }

    public Long getId() {
        return id;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", login='" + login.trim() + '\'' +
                ", password='" + password.trim() + '\'' +
                ", email='" + email.trim() + '\'' +
                ", firstName='" + firstName.trim() + '\'' +
                ", lastName='" + lastName.trim() + '\'' +
                ", role=" + role +
                '}';
    }


}
