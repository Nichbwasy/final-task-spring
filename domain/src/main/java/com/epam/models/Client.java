package com.epam.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "clients")
@Getter @Setter @NoArgsConstructor @ToString
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

    @NotNull(message = "This field is compulsory ")
    @Column(name = "first_name", length = 64, nullable = false)
    private String firstName;

    @NotNull(message = "This field is compulsory ")
    @Column(name = "last_name", length = 64, nullable = false)
    private String lastName;

    @Column(name = "enabled", nullable = false, columnDefinition="boolean default true")
    private Boolean enabled;

    @OneToMany(mappedBy = "client", targetEntity = CreditCard.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<CreditCard> creditCards;

    @ManyToMany
    @JoinTable(name = "clients_roles",
            joinColumns = @JoinColumn(name =  "client_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    private Collection<Role> roles;

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
