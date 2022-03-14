package com.epam.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.internal.util.stereotypes.Lazy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "This field is compulsory ")
    @Column(name = "username", unique = true, length = 64, nullable = false)
    private String username;

    @NotNull(message = "This field is compulsory ")
    @Length(min = 5, message = "Password should be at least 5 symbols!")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull(message = "This field is compulsory ")
    @Email(message = "Email isn't valid")
    @Column(name = "email", unique = true, length = 64, nullable = false)
    private String email;

    @NotNull(message = "This field is compulsory")
    @Column(name = "first_name", length = 64, nullable = false)
    private String firstName;

    @NotNull(message = "This field is compulsory ")
    @Column(name = "last_name", length = 64, nullable = false)
    private String lastName;

    @Column(name = "enabled", nullable = false, columnDefinition="boolean default true")
    private Boolean enabled;

    @OneToMany(mappedBy = "client", targetEntity = CreditCard.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonManagedReference
    private List<CreditCard> creditCards;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "clients_roles",
            joinColumns = @JoinColumn(name =  "client_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    private Collection<Role> roles;

    public Client(String username, String password, String email, String firstName, String lastName, Collection<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creditCards = new ArrayList<>();
        this.enabled = true;
        this.roles = roles;
    }

    public Client(String username, String password, String email, String firstName, String lastName, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creditCards = new ArrayList<>();
        this.enabled = true;
        this.roles = new ArrayList<>();
        this.roles.add(role);
    }

    public Client(Long id, String login, String password, String email, String firstName, String lastName) {
        this.id = id;
        this.username = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creditCards = new ArrayList<>();
        this.enabled = true;
        this.roles = new ArrayList<>();
    }
}
